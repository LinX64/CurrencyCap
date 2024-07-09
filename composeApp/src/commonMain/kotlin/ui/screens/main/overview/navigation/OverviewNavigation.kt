package ui.screens.main.overview.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.navigation.util.NavRoutes
import ui.screens.main.ai_predict.navigation.navigateToAiPredictScreen
import ui.screens.main.overview.OverviewRoute

fun NavController.navigateToOverviewScreen(navOptions: NavOptions) = navigate(NavRoutes.OVERVIEW, navOptions)

fun NavGraphBuilder.homeScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable(NavRoutes.OVERVIEW) {
        OverviewRoute(
            padding = padding,
            hazeState = hazeState,
            onSearchCardClicked = {
                navController.navigate(NavRoutes.EXPLORE) {
                    popUpTo(NavRoutes.OVERVIEW) { inclusive = true }
                }
            }, // TODO: Add animation
            onNewsItemClick = { url ->
                val encodedUrl = UrlEncoderUtil.encode(url)
                navController.navigate(NavRoutes.NEWS_DETAIL + "/$encodedUrl")
            },
            onCircleButtonClicked = { navController.navigateToAiPredictScreen() }
        )
    }
}