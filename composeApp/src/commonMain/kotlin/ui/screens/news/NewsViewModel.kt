package ui.screens.news

import androidx.lifecycle.viewModelScope
import data.remote.model.news.toEntity
import data.util.NetworkResult
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.news.NewsViewEvent.FetchNews
import ui.screens.news.NewsViewEvent.OnBookmarkArticle
import ui.screens.news.NewsViewEvent.OnRetry

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val articleLocalDataSource: ArticleLocalDataSource
) : MviViewModel<NewsViewEvent, NewsState, NewsNavigationEffect>(NewsState.Loading) {

    init {
        handleEvent(FetchNews)
    }

    override fun handleEvent(event: NewsViewEvent) {
        when (event) {
            is OnBookmarkArticle -> handleOnBookmarkClick(event.article, event.isBookmarked)
            FetchNews -> fetchNews()
            OnRetry -> fetchNews()
        }
    }

    private fun fetchNews() {
        newsRepository.getNews()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> setState { NewsState.Success(result.data) }
                    is NetworkResult.Error -> {
                        val news = result.data ?: emptyList()
                        val message = result.throwable.message ?: ""
                        if (news.isEmpty()) NewsState.Empty else NewsState.Error(message, news)
                    }

                    else -> setState { NewsState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleOnBookmarkClick(article: Article, isBookmarked: Boolean) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookmarked = isBookmarked)
            articleLocalDataSource.updateArticle(updatedArticle.toEntity())
        }
    }
}