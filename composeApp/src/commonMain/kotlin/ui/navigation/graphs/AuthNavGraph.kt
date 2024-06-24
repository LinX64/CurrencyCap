package ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ui.navigation.NavRoutes
import ui.screens.auth.login.navigation.loginScreen
import ui.screens.auth.register.navigation.registerScreen
import ui.screens.landing.navigation.landingScreen

@Composable
internal fun AuthNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LANDING
    ) {
        landingScreen(navController)

        loginScreen(
            padding = padding,
            navController = navController,
            onLoginSuccess = onLoginSuccess,
            onError = onError
        )
        registerScreen(
            padding = padding,
            navController = navController,
            onError = onError
        )
    }
}