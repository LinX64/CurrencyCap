package ui.screens.auth.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.util.NavRoutes
import ui.screens.auth.fill_profile.navigation.navigateToFillProfileScreen
import ui.screens.auth.login.navigation.navigateToLoginScreen
import ui.screens.auth.register.RegisterScreen

fun NavController.navigateToRegisterScreen() = navigate(NavRoutes.REGISTER)

fun NavGraphBuilder.registerScreen(
    navController: NavHostController,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.REGISTER) {
        RegisterScreen(
            onNavigateToFillProfile = { navController.navigateToFillProfileScreen() },
            navigateToLogin = { navController.navigateToLoginScreen() },
            onError = onError
        )
    }
}
