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
import ui.screens.main.news.NewsState.Empty
import ui.screens.main.news.NewsState.Loading
import ui.screens.main.news.NewsState.Success
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
        isEmpty = state.value is Empty,
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

    is Success -> {
        val articles = currentState.news.sortedBy { it.isBookmarked }
        items(articles.size) { index ->
            println("Is bookmarked: ${articles[index].isBookmarked}")

            NewsItem(
                article = articles[index],
                onNewsItemClick = onNewsItemClick,
                shouldShowBookmark = articles[index].isBookmarked,
                onBookmarkClick = { isBookmarked ->
                    newsViewModel.handleEvent(OnBookmarkArticle(articles[index], isBookmarked))
                }
            )
        }
    }

    is Loading -> {
        items(10) {
            NewsItem(
                isLoading = true,
                article = getDummyNewsItem(),
                onNewsItemClick = { },
                onBookmarkClick = { }
            )
        }
    }

    else -> Unit
}


