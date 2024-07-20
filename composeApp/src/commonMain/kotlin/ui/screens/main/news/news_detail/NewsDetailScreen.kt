package ui.screens.main.news.news_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.components.base.BaseModalBottomSheet
import ui.components.base.HandleNavigationEffect
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.news.news_detail.NewsDetailNavigationEffect.OpenBottomSheet
import ui.screens.main.news.news_detail.NewsDetailState.Error
import ui.screens.main.news.news_detail.NewsDetailState.Loading
import ui.screens.main.news.news_detail.NewsDetailState.Success
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
    var shouldShowBottomSheet by remember { mutableStateOf(false) }

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
                        onReadMoreClick = { shouldShowBottomSheet = true }
                    )
                }
            }

            is Error -> onError((state.value as Error).message)
            is Loading -> {
                item {
                    NewsDetailContent(
                        article = getDummyNewsItem(),
                        isLoading = true,
                        onReadMoreClick = { shouldShowBottomSheet = true }
                    )
                }
            }

            else -> Unit
        }
    }

    HandleNavigationEffect(newsDetailViewModel) { effect ->
        when (effect) {
            is OpenBottomSheet -> shouldShowBottomSheet = true
        }
    }

    BaseModalBottomSheet(
        isVisible = shouldShowBottomSheet,
        onDismiss = { shouldShowBottomSheet = false },
        containerColor = MaterialTheme.colorScheme.onSurface
    ) {
        NewsDetailSection(url = (state.value as Success).article.url)
    }
}
