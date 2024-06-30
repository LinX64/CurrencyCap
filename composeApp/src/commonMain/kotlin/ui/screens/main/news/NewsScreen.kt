package ui.screens.main.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.ErrorView
import ui.components.NewsItem
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.news.NewsViewEvent.OnBookmarkArticle
import ui.screens.main.news.NewsViewEvent.OnRetry
import util.getDummyNewsItem

@Composable
internal fun NewsScreen(
    padding: PaddingValues,
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    hazeState: HazeState,
    onNewsItemClick: (url: String) -> Unit
) {
    val state = newsViewModel.viewState.collectAsStateWithLifecycle()

    BaseGlassLazyColumn(
        hazeState = hazeState,
        padding = padding,
        isEmpty = state.value is NewsState.Empty,
        emptyContent = {
            ErrorView(
                message = "An error occurred",
                onRetry = { newsViewModel.handleEvent(OnRetry) }
            )
        }
    ) {
        newsScreenContent(state, newsViewModel, onNewsItemClick)
    }
}

private fun LazyListScope.newsScreenContent(
    state: State<NewsState>,
    newsViewModel: NewsViewModel,
    onNewsItemClick: (url: String) -> Unit
) = when (val currentState = state.value) {
    is NewsState.Success -> {
        val articles = currentState.news
        items(articles.size) { index ->
            NewsItem(
                article = articles[index],
                onNewsItemClick = { onNewsItemClick(articles[index].url) },
                shouldShowBookmark = articles[index].isBookmarked,
                onBookmarkClick = { isBookmarked ->
                    newsViewModel.handleEvent(OnBookmarkArticle(articles[index], isBookmarked))
                }
            )
        }
    }

    is NewsState.Loading -> {
        items(10) {
            NewsItem(
                isLoading = true,
                article = getDummyNewsItem(),
                onNewsItemClick = { },
                onBookmarkClick = { },
                shouldShowBookmark = false
            )
        }
    }

    is NewsState.Error -> {
        items(currentState.news.size) { index ->
            NewsItem(
                article = currentState.news[index],
                onNewsItemClick = { onNewsItemClick(currentState.news[index].url) },
                onBookmarkClick = { newsViewModel.handleEvent(OnBookmarkArticle(currentState.news[index], false)) },
                shouldShowBookmark = currentState.news[index].isBookmarked
            )
        }
    }

    else -> Unit
}


