package ui.screens.main.news.news_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import domain.repository.NewsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.navigation.util.ENCODED_URL
import ui.screens.main.news.news_detail.NewsDetailNavigationEffect.OpenBottomSheet
import ui.screens.main.news.news_detail.NewsDetailNavigationEffect.ShowError
import ui.screens.main.news.news_detail.NewsDetailState.Loading
import ui.screens.main.news.news_detail.NewsDetailState.Success
import ui.screens.main.news.news_detail.NewsDetailViewEvent.FetchNews
import ui.screens.main.news.news_detail.NewsDetailViewEvent.OnReadMoreClick

class NewsDetailViewModel(
    private val newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<NewsDetailViewEvent, NewsDetailState, NewsDetailNavigationEffect>(Loading) {

    val url: String = savedStateHandle.get<String>(ENCODED_URL) ?: ""

    init {
        handleEvent(FetchNews(url))
    }

    override fun handleEvent(event: NewsDetailViewEvent) {
        when (event) {
            is FetchNews -> fetchNews(event.url)
            is OnReadMoreClick -> setEffect(OpenBottomSheet(event.url))
        }
    }

    private fun fetchNews(url: String) {
        newsRepository.getArticleByUrl(url)
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> setState { Success(result.data) }
                    is NetworkResult.Error -> setEffect(ShowError(result.throwable.message ?: "Error while fetching news"))
                    else -> setState { Loading }
                }
            }
            .launchIn(viewModelScope)
    }
}