package ui.screens.news.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.chrisbanes.haze.HazeState
import ui.navigation.NavRoutes

fun NavController.navigateToNewsDetailScreen() = navigate(NavRoutes.NEWS_DETAIL)

fun NavGraphBuilder.newsDetailScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable(
        route = "${NavRoutes.NEWS_DETAIL}/{url}",
        arguments = listOf(navArgument("url") { type = NavType.StringType })
    ) {
        newsDetailScreen(
            padding = padding,
            hazeState = hazeState,
            navController = navController
        )
    }
}
