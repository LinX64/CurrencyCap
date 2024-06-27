package ui.screens.news

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.main.BaseBlurLazyColumn
import ui.screens.news.components.NewsItem

@Composable
internal fun NewsScreen(
    padding: PaddingValues,
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    hazeState: HazeState
) {
    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding,
    ) {
        items(10) {
            NewsItem(
                onClick = {},
            )
        }
    }
}



