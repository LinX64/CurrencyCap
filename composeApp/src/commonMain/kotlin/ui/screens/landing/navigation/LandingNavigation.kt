package ui.screens.landing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.auth.login.navigation.navigateToLoginScreen
import ui.screens.auth.register.navigation.navigateToRegisterScreen
import ui.screens.landing.LandingScreen

fun NavGraphBuilder.landingScreen(navController: NavHostController) {
    composable(NavRoutes.LANDING) {
        BlurBackground {
            LandingScreen(
                onLoginClick = { navController.navigateToLoginScreen() },
                onCreateAccountClick = { navController.navigateToRegisterScreen() }
            )
        }
    }
}
