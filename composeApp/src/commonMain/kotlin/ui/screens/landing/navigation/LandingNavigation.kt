package ui.screens.landing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.util.NavRoutes
import ui.screens.auth.login.navigation.navigateToLoginScreen
import ui.screens.auth.register.navigation.navigateToRegisterScreen
import ui.screens.landing.LandingScreen

fun NavGraphBuilder.landingScreen(navController: NavHostController) {
    composable(NavRoutes.LANDING) {
        LandingScreen(
            onLoginClick = { navController.navigateToLoginScreen() },
            onSignUpClick = { navController.navigateToRegisterScreen() }
        )
    }
}
