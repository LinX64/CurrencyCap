package ui.screens.news.news_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.HandleNavigationEffect
import ui.components.main.BaseGlassLazyColumn
import ui.screens.news.news_detail.NewsDetailNavigationEffect.OpenBrowser
import ui.screens.news.news_detail.NewsDetailViewEvent.OnReadMoreClick
import ui.screens.news.news_detail.components.NewsDetailContent
import util.getDummyNewsItem

@Composable
internal fun NewsDetailScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    newsDetailViewModel: NewsDetailViewModel = koinViewModel<NewsDetailViewModel>(),
    onError: (String) -> Unit,
    decodedUrl: String
) {
    val state = newsDetailViewModel.viewState.collectAsStateWithLifecycle()
    BaseGlassLazyColumn(
        hazeState = hazeState,
        padding = padding,
    ) {
        when (state.value) {
            is NewsDetailState.Success -> {
                val article = (state.value as NewsDetailState.Success).article
                item {
                    NewsDetailContent(
                        article = article,
                        imageUrl = article.urlToImage,
                        onReadMoreClick = { newsDetailViewModel.handleEvent(OnReadMoreClick) }
                    )
                }
            }

            is NewsDetailState.Error -> onError((state.value as NewsDetailState.Error).message)
            is NewsDetailState.Loading -> {
                item {
                    NewsDetailContent(
                        article = getDummyNewsItem(),
                        isLoading = true,
                        onReadMoreClick = { }
                    )
                }
            }

            else -> Unit
        }
    }

    HandleNavigationEffect(newsDetailViewModel) { effect ->
        when (effect) {
            is OpenBrowser -> {
                /*TODO*/
            }
        }
    }
}
