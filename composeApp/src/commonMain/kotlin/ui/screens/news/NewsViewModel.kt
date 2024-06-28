package ui.screens.news

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.repository.NewsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.screens.news.NewsViewEvent.FetchNews

class NewsViewModel(
    private val newsRepository: NewsRepository
) : MviViewModel<NewsViewEvent, NewsState, NewsNavigationEffect>(NewsState.Loading) {

    init {
        handleEvent(FetchNews)
    }

    override fun handleEvent(event: NewsViewEvent) {
        when (event) {
            FetchNews -> fetchNews()
        }
    }

    private fun fetchNews() {
        newsRepository.getNews()
            .asResult()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> setState { NewsState.Success(result.data) }
                    is NetworkResult.Error -> {
                        setState { NewsState.Error(result.exception.message ?: "Error while fetching news") }
                    }

                    else -> setState { NewsState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }
}