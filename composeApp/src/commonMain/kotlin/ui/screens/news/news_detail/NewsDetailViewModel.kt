package ui.screens.news.news_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.repository.NewsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.screens.news.news_detail.NewsDetailViewEvent.FetchNews

class NewsDetailViewModel(
    private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : MviViewModel<NewsDetailViewEvent, NewsDetailState, NewsDetailNavigationEffect>(NewsDetailState.Loading) {

    private val url: String = savedStateHandle.get<String>("url") ?: ""

    init {
        handleEvent(FetchNews(url))
    }

    override fun handleEvent(event: NewsDetailViewEvent) {
        when (event) {
            is FetchNews -> fetchNews(event.url)
        }
    }

    private fun fetchNews(url: String) {
        newsRepository.getArticleByUrl(url)
            .asResult()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> setState { NewsDetailState.Success(result.data) }
                    is NetworkResult.Error -> setState {
                        NewsDetailState.Error(
                            result.exception.message ?: "Error while fetching news"
                        )
                    }

                    else -> setState { NewsDetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }
}