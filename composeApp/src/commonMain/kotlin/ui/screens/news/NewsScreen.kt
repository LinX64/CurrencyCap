package ui.screens.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.HandleNavigationEffect
import ui.components.NewsItem
import ui.components.main.BaseBlurLazyColumn
import ui.screens.news.NewsViewEvent.OnBookmarkArticle
import util.getDummyNewsItem

@Composable
internal fun NewsScreen(
    padding: PaddingValues,
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    hazeState: HazeState,
    onNewsItemClick: (url: String) -> Unit,
    onError: (message: String) -> Unit
) {
    val state = newsViewModel.viewState.collectAsStateWithLifecycle()

    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding
    ) {
        when (val currentState = state.value) {
            is NewsState.Success -> {
                val articles = currentState.news
                items(articles.size) { index ->
                    NewsItem(
                        article = articles[index],
                        onNewsItemClick = { onNewsItemClick(articles[index].url) },
                        onBookmarkClick = { isBookmarked ->
                            newsViewModel.handleEvent(OnBookmarkArticle(articles[index], isBookmarked))
                        },
                        shouldShowBookmark = articles[index].isBookmarked
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
                //onError(currentState.message)
                // TODO: Show message once
                // Show cached news
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
    }

    HandleNavigationEffect(newsViewModel) { effect ->
        when (effect) {
            is NewsNavigationEffect.NavigateToNewsDetail -> {
                /*TODO*/
            }
        }
    }
}
