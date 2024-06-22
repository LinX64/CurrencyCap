package ui.screens.overview.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.overview.OverviewScreen

fun NavController.navigateToHomeScreen() = navigate(NavRoutes.MARKET_OVERVIEW)

fun NavGraphBuilder.overviewScreen(padding: PaddingValues) {
    composable(
        route = "${NavRoutes.MARKET_OVERVIEW}/{uid}",
        arguments = listOf(
            navArgument("uid") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        BlurBackground {
            OverviewScreen(padding = padding)
        }
    }
}

//fun NavGraphBuilder.overviewScreen(padding: PaddingValues) {
//    composable(
//        route = NavRoutes.MARKET_OVERVIEW,
//    ) {
//        BlurBackground {
//            OverviewScreen(padding = padding)
//        }
//    }
//}