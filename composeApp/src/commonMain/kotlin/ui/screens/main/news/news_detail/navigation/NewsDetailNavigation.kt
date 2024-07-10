package ui.screens.main.news.news_detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.chrisbanes.haze.HazeState
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.navigation.util.Screens.NewsDetail
import ui.screens.main.news.news_detail.NewsDetailScreen

fun NavGraphBuilder.newsDetailScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    onError: (String) -> Unit
) {
    composable<NewsDetail> { backStackEntry ->
        val newsDetail: NewsDetail = backStackEntry.toRoute()
        val decodedUrl = UrlEncoderUtil.decode(newsDetail.url)

        NewsDetailScreen(
            padding = padding,
            hazeState = hazeState,
            onError = onError,
            decodedUrl = decodedUrl
        )
    }
}
