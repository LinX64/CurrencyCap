package ui.screens.initial.landing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.util.NavRoutes
import ui.screens.initial.landing.LandingScreen
import ui.screens.initial.login.navigation.navigateToLoginScreen
import ui.screens.initial.register.navigation.navigateToRegisterScreen

fun NavGraphBuilder.landingScreen(navController: NavHostController) {
    composable(NavRoutes.LANDING) {
        LandingScreen(
            onLoginClick = { navController.navigateToLoginScreen() },
            onSignUpClick = { navController.navigateToRegisterScreen() }
        )
    }
}
