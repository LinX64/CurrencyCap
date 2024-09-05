package ui.screens.main.news

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
import ui.components.base.HandleNavigationEffect
import ui.screens.main.news.NewsNavigationEffect.ShowBookmarkConfirmation
import ui.screens.main.news.NewsState.Empty
import ui.screens.main.news.NewsState.Loading
import ui.screens.main.news.NewsState.Success
import ui.screens.main.news.NewsViewEvent.OnBookmarkArticle
import ui.screens.main.news.NewsViewEvent.OnRetry
import util.getDummyNewsItem

@Composable
internal fun NewsRoute(
    newsViewModel: NewsViewModel = koinViewModel(),
    hazeState: HazeState,
    onNewsItemClick: (url: String) -> Unit,
    showBookmarkConfirmationSnakeBar: (Boolean) -> Unit
) {
    val state = newsViewModel.viewState.collectAsStateWithLifecycle()

    NewsScreen(
        state = state,
        hazeState = hazeState,
        onNewsItemClick = onNewsItemClick,
        handleEvent = newsViewModel::handleEvent
    )

    HandleNavigationEffect(newsViewModel) { effect ->
        when (effect) {
            is ShowBookmarkConfirmation -> showBookmarkConfirmationSnakeBar(effect.isBookmarked)
        }
    }
}

@Composable
internal fun NewsScreen(
    state: State<NewsState>,
    hazeState: HazeState,
    onNewsItemClick: (url: String) -> Unit,
    handleEvent: (NewsViewEvent) -> Unit
) {
    BaseGlassLazyColumn(
        hazeState = hazeState,
        isEmpty = state.value is Empty,
        emptyContent = {
            ErrorView(
                message = stringResource(Res.string.an_error_occurred),
                onRetry = { handleEvent(OnRetry) }
            )
        }
    ) {
        newsScreenContent(
            state = state,
            onNewsItemClick = onNewsItemClick,
            handleEvent = handleEvent
        )
    }
}

private fun LazyListScope.newsScreenContent(
    state: State<NewsState>,
    onNewsItemClick: (url: String) -> Unit,
    handleEvent: (NewsViewEvent) -> Unit
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
                    handleEvent(OnBookmarkArticle(articles[index], isBookmarked))
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

    is Error -> {
        item {
            ErrorView(
                message = stringResource(Res.string.an_error_occurred),
                onRetry = { handleEvent(OnRetry) }
            )
        }
    }

    else -> Unit
}


