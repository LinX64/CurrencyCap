package ui.screens.auth.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.auth.fill_profile.navigation.navigateToFillProfileScreen
import ui.screens.auth.login.navigation.navigateToLoginScreen
import ui.screens.auth.register.RegisterScreen

fun NavController.navigateToRegisterScreen() = navigate(NavRoutes.REGISTER)

fun NavGraphBuilder.registerScreen(
    navController: NavHostController,
    padding: PaddingValues,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.REGISTER) {
        BlurBackground {
            RegisterScreen(
                padding = padding,
                onNavigateToFillProfile = { navController.navigateToFillProfileScreen() },
                navigateToLogin = { navController.navigateToLoginScreen() },
                onError = onError
            )
        }
    }
}
