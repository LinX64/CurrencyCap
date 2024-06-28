package ui.screens.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import data.model.news.Article
import data.model.news.Source
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.HandleNavigationEffect
import ui.components.main.BaseBlurLazyColumn
import ui.screens.news.components.NewsItem

@Composable
internal fun NewsScreen(
    padding: PaddingValues,
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    hazeState: HazeState
) {
    val state = newsViewModel.viewState.collectAsStateWithLifecycle()

    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding
    ) {
        if (state.value is NewsState.Success) {
            val news = (state.value as NewsState.Success).news

            items(news.size) { newsItem ->
                NewsItem(
                    article = news[newsItem],
                    onNewsItemClick = { sourceId ->
                        /*TODO*/
                    }
                )
            }
        }

        if (state.value is NewsState.Loading) {
            items(10) {
                NewsItem(
                    isLoading = true,
                    article = getDummyNewsItem(),
                    onNewsItemClick = { sourceId -> }
                )
            }
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



