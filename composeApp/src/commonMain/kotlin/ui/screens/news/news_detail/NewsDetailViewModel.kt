package ui.screens.news.news_detail

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.repository.NewsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.screens.news.news_detail.NewsDetailViewEvent.FetchNews
import ui.screens.news.news_detail.NewsDetailViewEvent.OnReadMoreClick

class NewsDetailViewModel(
    private val newsRepository: NewsRepository,
) : MviViewModel<NewsDetailViewEvent, NewsDetailState, NewsDetailNavigationEffect>(NewsDetailState.Loading) {

    init {
        handleEvent(FetchNews("https://readwrite.com/new-crypto-presale-pepe-unchained-goes-live-what-is-pepu-meme-token/"))
    }

    override fun handleEvent(event: NewsDetailViewEvent) {
        when (event) {
            is FetchNews -> fetchNews(event.url)
            is OnReadMoreClick -> handleReadMoreClick(event.url)
        }
    }

    private fun handleReadMoreClick(url: String) {
        // TODO: Should open the article in a browser
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