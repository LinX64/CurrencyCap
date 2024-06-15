package ui.screens.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.home.HomeScreen

fun NavController.navigateToHomeScreen() = navigate(NavRoutes.HOME)

fun NavGraphBuilder.homeScreen(padding: PaddingValues) {
    composable(
        route = "${NavRoutes.HOME}/{userId}",
        arguments = listOf(
            navArgument("userId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        BlurBackground {
            HomeScreen(padding = padding)
        }
    }
}
