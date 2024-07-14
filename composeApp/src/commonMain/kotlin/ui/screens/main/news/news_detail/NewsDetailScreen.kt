package ui.screens.main.news.news_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.components.base.HandleNavigationEffect
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.news.news_detail.NewsDetailNavigationEffect.OpenBrowser
import ui.screens.main.news.news_detail.NewsDetailState.Error
import ui.screens.main.news.news_detail.NewsDetailState.Loading
import ui.screens.main.news.news_detail.NewsDetailState.Success
import ui.screens.main.news.news_detail.NewsDetailViewEvent.OnReadMoreClick
import ui.screens.main.news.news_detail.components.NewsDetailContent
import util.getDummyNewsItem

@Composable
internal fun NewsDetailScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    decodedUrl: String,
    newsDetailViewModel: NewsDetailViewModel = koinViewModel { parametersOf(decodedUrl) },
    onError: (String) -> Unit,
) {
    val state = newsDetailViewModel.viewState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    BaseGlassLazyColumn(
        hazeState = hazeState,
        padding = padding,
    ) {
        when (state.value) {
            is Success -> {
                val article = (state.value as Success).article
                item {
                    NewsDetailContent(
                        article = article,
                        imageUrl = article.urlToImage,
                        onReadMoreClick = { newsDetailViewModel.handleEvent(OnReadMoreClick(it)) }
                    )
                }
            }

            is Error -> onError((state.value as Error).message)
            is Loading -> {
                item {
                    NewsDetailContent(
                        article = getDummyNewsItem(),
                        isLoading = true,
                        onReadMoreClick = { newsDetailViewModel.handleEvent(OnReadMoreClick(it)) }
                    )
                }
            }

            else -> Unit
        }
    }

    HandleNavigationEffect(newsDetailViewModel) { effect ->
        when (effect) {
            is OpenBrowser -> uriHandler.openUri(effect.url)
        }
    }
}
