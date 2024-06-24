package ui.screens.overview.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.overview.OverviewScreen

fun NavController.navigateToOverviewScreen() = navigate(NavRoutes.MARKET_OVERVIEW)

fun NavGraphBuilder.overviewScreen(padding: PaddingValues) {
    composable(NavRoutes.MARKET_OVERVIEW) {
        BlurBackground {
            OverviewScreen(padding = padding)
        }
    }
}