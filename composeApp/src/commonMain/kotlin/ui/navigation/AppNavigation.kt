package ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.screens.HomeRoute

@Composable
internal fun AppNavigation(
    navController: NavHostController,
    padding: PaddingValues
) {
    val hazeState = remember { HazeState() }
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.ROUTE,
        modifier = Modifier
            .padding(padding)
            .consumeWindowInsets(padding)

    ) {
        composable(NavRoutes.Home.ROUTE) {
            HomeRoute()
        }
    }
}