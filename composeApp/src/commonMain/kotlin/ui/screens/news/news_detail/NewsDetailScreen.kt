package ui.screens.news.news_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.HandleNavigationEffect
import ui.components.main.BaseBlurLazyColumn
import ui.screens.news.news_detail.NewsDetailViewEvent.OnReadMoreClick
import ui.screens.news.news_detail.components.NewsDetailHeader
import util.getDummyNewsItem

@Composable
internal fun NewsDetailScreen(
    padding: PaddingValues,
    newsDetailViewModel: NewsDetailViewModel = koinViewModel<NewsDetailViewModel>(),
    hazeState: HazeState,
    url: String,
    onError: (String) -> Unit
) {
    val state = newsDetailViewModel.viewState.collectAsStateWithLifecycle()
    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding,
    ) {
        when (state.value) {
            is NewsDetailState.Success -> {
                val article = (state.value as NewsDetailState.Success).article
                item {
                    NewsDetailHeader(
                        article = article,
                        imageUrl = article.urlToImage,
                        onReadMoreClick = { newsDetailViewModel.handleEvent(OnReadMoreClick(url)) }
                    )
                }
            }

            is NewsDetailState.Error -> onError((state.value as NewsDetailState.Error).message)
            is NewsDetailState.Loading -> {
                item {
                    NewsDetailHeader(
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
            is NewsDetailNavigationEffect.NavigateToNewsDetailDetail -> {
                /*TODO*/
            }
        }
    }
}
