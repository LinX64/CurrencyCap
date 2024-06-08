package ui.screens.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.home.HomeScreen

fun NavController.navigateToHomeScreen() = navigate(NavRoutes.HOME)

fun NavGraphBuilder.homeScreen(padding: PaddingValues) {
    composable(NavRoutes.HOME) {
        BlurBackground {
            HomeScreen(padding = padding)
        }
    }
}
