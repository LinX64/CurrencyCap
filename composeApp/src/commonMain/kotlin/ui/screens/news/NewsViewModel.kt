package ui.screens.news

import androidx.lifecycle.viewModelScope
import data.remote.model.news.toEntity
import data.util.NetworkResult
import data.util.asResult
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.news.NewsViewEvent.FetchNews
import ui.screens.news.NewsViewEvent.OnBookmarkArticle

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val articleLocalDataSource: ArticleLocalDataSource
) : MviViewModel<NewsViewEvent, NewsState, NewsNavigationEffect>(NewsState.Loading) {

    init {
        handleEvent(FetchNews)
    }

    override fun handleEvent(event: NewsViewEvent) {
        when (event) {
            FetchNews -> fetchNews()
            is OnBookmarkArticle -> handleOnBookmarkClick(event.article)
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

    private fun handleOnBookmarkClick(article: Article, isBookmarked: Boolean = true) {
        viewModelScope.launch {
            articleLocalDataSource.insertArticle(
                article = article.copy(isBookmarked = isBookmarked).toEntity()
            )
        }
    }
}