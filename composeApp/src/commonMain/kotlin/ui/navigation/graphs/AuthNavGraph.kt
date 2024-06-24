package ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ui.navigation.NavRoutes
import ui.navigation.authGraph
import ui.screens.landing.navigation.landingScreen

@Composable
internal fun AuthNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    onError: (message: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LANDING
    ) {
        landingScreen(navController)

        authGraph(
            padding = padding,
            navController = navController,
            onError = onError
        )
    }
}