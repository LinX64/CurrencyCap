package ui.screens.main.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.an_error_occurred
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.resources.stringResource
import ui.components.ErrorView
import ui.components.NewsItem
import ui.components.base.BaseGlassLazyColumn
import ui.screens.main.news.NewsState.Empty
import ui.screens.main.news.NewsState.Loading
import ui.screens.main.news.NewsState.Success
import ui.screens.main.news.NewsViewEvent.OnBookmarkArticle
import ui.screens.main.news.NewsViewEvent.OnRetry
import util.getDummyNewsItem

@Composable
internal fun NewsRoute(
    padding: PaddingValues,
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    hazeState: HazeState,
    onNewsItemClick: (url: String) -> Unit
) {
    val state = newsViewModel.viewState.collectAsStateWithLifecycle()

    NewsScreen(
        padding = padding,
        state = state,
        newsViewModel = newsViewModel,
        hazeState = hazeState,
        onNewsItemClick = onNewsItemClick
    )
}

@Composable
internal fun NewsScreen(
    padding: PaddingValues,
    state: State<NewsState>,
    newsViewModel: NewsViewModel,
    hazeState: HazeState,
    onNewsItemClick: (url: String) -> Unit
) {
    BaseGlassLazyColumn(
        hazeState = hazeState,
        padding = padding,
        isEmpty = state.value is Empty,
        emptyContent = {
            ErrorView(
                message = stringResource(Res.string.an_error_occurred),
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
        val articles = currentState.news
        items(
            count = articles.size,
            key = { articles[it].url }
        ) { index ->
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


