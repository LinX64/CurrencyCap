package ui.screens.main.news.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.navigation.Screens.News
import ui.navigation.util.NavRoutes
import ui.screens.main.news.NewsScreen

fun NavController.navigateToNewsScreen(navOptions: NavOptions) = navigate(NavRoutes.NEWS, navOptions)

fun NavGraphBuilder.newsScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable<News> {
        NewsScreen(
            padding = padding,
            hazeState = hazeState,
            onNewsItemClick = { url ->
                val encodedUrl = UrlEncoderUtil.encode(url)
                navController.navigate(NavRoutes.NEWS_DETAIL + "/$encodedUrl")
            }
        )
    }
}

