package ui.screens.news.news_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.repository.NewsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.navigation.ENCODED_URL
import ui.screens.news.news_detail.NewsDetailViewEvent.FetchNews
import ui.screens.news.news_detail.NewsDetailViewEvent.OnReadMoreClick

class NewsDetailViewModel(
    private val newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<NewsDetailViewEvent, NewsDetailState, NewsDetailNavigationEffect>(NewsDetailState.Loading) {

    val url: String = savedStateHandle.get<String>(ENCODED_URL) ?: ""

    init {
        handleEvent(FetchNews(url))
    }

    override fun handleEvent(event: NewsDetailViewEvent) {
        when (event) {
            is FetchNews -> fetchNews(event.url)
            is OnReadMoreClick -> setEffect(NewsDetailNavigationEffect.OpenBrowser(url))
        }
    }

    private fun fetchNews(url: String) {
        newsRepository.getArticleByUrl(url)
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> setState { NewsDetailState.Success(result.data) }
                    is NetworkResult.Error -> setState {
                        NewsDetailState.Error(result.throwable.message ?: "Error while fetching news")
                    }

                    else -> setState { NewsDetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }
}