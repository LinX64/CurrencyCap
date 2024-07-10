package ui.screens.main.news

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
import ui.screens.main.news.NewsState.Error
import ui.screens.main.news.NewsState.Loading
import ui.screens.main.news.NewsState.Success
import ui.screens.main.news.NewsViewEvent.FetchNews
import ui.screens.main.news.NewsViewEvent.OnBookmarkArticle
import ui.screens.main.news.NewsViewEvent.OnRetry

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val articleLocalDataSource: ArticleLocalDataSource
) : MviViewModel<NewsViewEvent, NewsState, NewsNavigationEffect>(Loading) {

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
                    is NetworkResult.Success -> setState { Success(result.data.sortedBy { it.publishedAt }.reversed()) }
                    is NetworkResult.Error -> {
                        val news = result.data?.sortedBy { it.publishedAt }?.reversed() ?: emptyList()
                        val message = result.throwable.message ?: ""

                        if (news.isNotEmpty()) setState { Success(news) }
                        else setState { Error(message) }
                    }

                    else -> setState { Loading }
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