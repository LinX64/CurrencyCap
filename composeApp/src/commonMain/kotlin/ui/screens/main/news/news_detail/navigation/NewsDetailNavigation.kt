package ui.screens.main.news.news_detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.screens.main.news.news_detail.NewsDetailScreen

fun NavGraphBuilder.newsDetailScreen(
    hazeState: HazeState,
    onError: (String) -> Unit
) {
    composable<NewsDetail> { backStackEntry ->
        val newsDetail: NewsDetail = backStackEntry.toRoute()
        val decodedUrl = UrlEncoderUtil.decode(newsDetail.url)

        NewsDetailScreen(
            hazeState = hazeState,
            onError = onError,
            decodedUrl = decodedUrl
        )
    }
}

@Serializable
data class NewsDetail(val url: String)