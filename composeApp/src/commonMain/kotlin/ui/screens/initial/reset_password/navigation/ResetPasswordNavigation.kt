package ui.screens.initial.reset_password.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.util.Screen.ResetPassword
import ui.screens.initial.login.navigation.navigateToLoginScreen
import ui.screens.initial.reset_password.ResetPasswordScreen

fun NavController.navigateToResetPassword() = navigate(ResetPassword)

fun NavGraphBuilder.resetPasswordScreen(
    navController: NavHostController,
    padding: PaddingValues,
    onError: (message: String) -> Unit
) {
    composable<ResetPassword> {
        ResetPasswordScreen(
            padding = padding,
            onMessage = onError,
            onNavigateToLogin = { navController.navigateToLoginScreen() }
        )
    }
}
