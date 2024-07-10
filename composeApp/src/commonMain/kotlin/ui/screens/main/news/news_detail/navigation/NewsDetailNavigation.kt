package ui.screens.main.news.news_detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.chrisbanes.haze.HazeState
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.navigation.Screens
import ui.navigation.Screens.NewsDetail
import ui.navigation.util.NavRoutes
import ui.screens.main.news.news_detail.NewsDetailScreen

fun NavController.navigateToNewsDetailScreen() = navigate(NavRoutes.NEWS_DETAIL)

fun NavGraphBuilder.newsDetailScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    onError: (String) -> Unit
) {
    composable<NewsDetail> { backStackEntry ->
        val profile: Screens.Profile = backStackEntry.toRoute()
        val decodedUrl = UrlEncoderUtil.decode(profile.url)

        NewsDetailScreen(
            padding = padding,
            hazeState = hazeState,
            onError = onError,
            decodedUrl = decodedUrl
        )
    }
}
