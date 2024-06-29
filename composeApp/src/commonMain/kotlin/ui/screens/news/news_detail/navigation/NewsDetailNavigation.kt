package ui.screens.news.news_detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.chrisbanes.haze.HazeState
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.navigation.ENCODED_URL
import ui.navigation.NavRoutes
import ui.screens.news.news_detail.NewsDetailScreen

fun NavController.navigateToNewsDetailScreen() = navigate(NavRoutes.NEWS_DETAIL)

fun NavGraphBuilder.newsDetailScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController,
    onError: (String) -> Unit
) {
    composable(
        route = "${NavRoutes.NEWS_DETAIL}/{$ENCODED_URL}",
        arguments = listOf(
            navArgument(name = ENCODED_URL) {
                nullable = false
                defaultValue = ""
                type = NavType.StringType
            })
    ) { backStackEntry ->
        val encodedUrl = backStackEntry.arguments?.getString(ENCODED_URL) ?: ""
        val decodedUrl = UrlEncoderUtil.decode(encodedUrl)

        NewsDetailScreen(
            padding = padding,
            hazeState = hazeState,
            onError = onError,
            decodedUrl = decodedUrl
        )
    }
}
