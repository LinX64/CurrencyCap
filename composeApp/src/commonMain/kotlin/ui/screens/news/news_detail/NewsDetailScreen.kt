package ui.screens.news.news_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import data.model.news.Article
import data.model.news.Source
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.HandleNavigationEffect
import ui.components.main.BaseBlurLazyColumn

@Composable
internal fun NewsDetailScreen(
    padding: PaddingValues,
    newsDetailViewModel: NewsDetailViewModel = koinViewModel<NewsDetailViewModel>(),
    hazeState: HazeState
) {
    val state = newsDetailViewModel.viewState.collectAsStateWithLifecycle()

    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding
    ) {
        if (state.value is NewsDetailState.Success) {
            val article = (state.value as NewsDetailState.Success).article

            item {
                // NewsDetailHeader(article = article)
            }

            item {
                // NewsDetailContent(article = article)
            }
        }
    }

    if (state.value is NewsDetailState.Loading) {

    }

    HandleNavigationEffect(newsDetailViewModel) { effect ->
        when (effect) {
            is NewsDetailNavigationEffect.NavigateToNewsDetailDetail -> {
                /*TODO*/
            }
        }
    }
}

private fun getDummyNewsItem(): Article = Article(
    source = Source(
        id = "id",
        name = "CoinDesk"
    ),
    author = "author",
    title = "Lorem ipsum dolor sit amet, consectetur adipiscing",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididu",
    url = "url",
    urlToImage = "urlToImage",
    publishedAt = "publishedAt",
    content = "content"
)



