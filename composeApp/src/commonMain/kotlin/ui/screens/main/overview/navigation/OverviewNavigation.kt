package ui.screens.main.overview.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.navigation.util.Screen.CryptoDetail
import ui.navigation.util.Screen.Explore
import ui.navigation.util.Screen.NewsDetail
import ui.navigation.util.Screen.Overview
import ui.screens.main.ai_predict.navigation.navigateToAiPredictScreen
import ui.screens.main.overview.OverviewRoute

fun NavController.navigateToOverviewScreen(navOptions: NavOptions) = navigate(Overview, navOptions)

fun NavGraphBuilder.overviewScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable<Overview> {
        OverviewRoute(
            padding = padding,
            hazeState = hazeState,
            onSearchCardClicked = {
                navController.navigate(Explore) {
                    popUpTo(Overview) { inclusive = true }
                }
            },
            onNewsItemClick = { url ->
                val encodedUrl = UrlEncoderUtil.encode(url)
                navController.navigate(NewsDetail(encodedUrl))
            },
            onCircleButtonClicked = { navController.navigateToAiPredictScreen() },
            onCryptoItemClick = { symbol -> navController.navigate(CryptoDetail(symbol)) }
        )
    }
}
