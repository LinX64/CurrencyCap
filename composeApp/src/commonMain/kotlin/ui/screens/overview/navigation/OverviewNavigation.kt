package ui.screens.overview.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.navigation.NavRoutes
import ui.screens.overview.OverviewScreen

fun NavController.navigateToOverviewScreen() = navigate(NavRoutes.HOME)

fun NavGraphBuilder.homeScreen(padding: PaddingValues) {
    composable(NavRoutes.HOME) {
        OverviewScreen(padding = padding)
    }
}