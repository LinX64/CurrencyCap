package ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui.screens.HomeScreen

@Composable
internal fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.ROUTE
    ) {
        composable(NavRoutes.Home.ROUTE) {
            HomeScreen()
        }
    }
}