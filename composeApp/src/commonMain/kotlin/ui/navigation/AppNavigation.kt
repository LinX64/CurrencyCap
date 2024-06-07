package ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.screens.main.HomeRoute

@Composable
internal fun AppNavigation(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.ROUTE,
        modifier = Modifier
            .padding(padding)
            .consumeWindowInsets(padding)
    ) {
        composable(NavRoutes.Home.ROUTE) {
            BlurBackground {
                HomeRoute()
            }
        }
    }
}