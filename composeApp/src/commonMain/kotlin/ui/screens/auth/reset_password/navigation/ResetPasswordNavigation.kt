package ui.screens.auth.reset_password.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.NavRoutes
import ui.screens.auth.login.navigation.navigateToLoginScreen
import ui.screens.auth.reset_password.ResetPasswordScreen

fun NavController.navigateToResetPassword() = navigate(NavRoutes.RESET_PASSWORD)

fun NavGraphBuilder.resetPasswordScreen(
    navController: NavHostController,
    padding: PaddingValues,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.RESET_PASSWORD) {
        ResetPasswordScreen(
            padding = padding,
            onMessage = onError,
            onNavigateToLogin = { navController.navigateToLoginScreen() }
        )
    }
}
