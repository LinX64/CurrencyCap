package ui.screens.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import domain.model.Article
import domain.model.Source
import ui.components.HandleNavigationEffect
import ui.components.main.BaseBlurLazyColumn
import ui.screens.news.components.NewsItem

@Composable
internal fun NewsScreen(
    padding: PaddingValues,
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    hazeState: HazeState,
    onNewsItemClick: (url: String) -> Unit
) {
    val state = newsViewModel.viewState.collectAsStateWithLifecycle()

    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding
    ) {
        if (state.value is NewsState.Success) {
            val articles = (state.value as NewsState.Success).news
            items(articles.size) { newsItem ->
                NewsItem(
                    article = articles[newsItem],
                    onNewsItemClick = { url -> onNewsItemClick(url) }
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

private fun getDummyNewsItem() = Article(
    author = "author",
    content = "content",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididu",
    publishedAt = "publishedAt",
    sourceDto = Source(
        id = "id",
        name = "CoinDesk"
    ),
    title = "Lorem ipsum dolor sit amet, consectetur adipiscing",
    url = "url",
    urlToImage = "urlToImage",
)



