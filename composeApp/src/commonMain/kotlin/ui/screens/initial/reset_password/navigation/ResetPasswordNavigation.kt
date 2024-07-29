package ui.screens.initial.reset_password.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ui.screens.initial.login.navigation.navigateToLoginScreen
import ui.screens.initial.reset_password.ResetPasswordScreen

fun NavController.navigateToResetPassword() = navigate(ResetPassword)

fun NavGraphBuilder.resetPasswordScreen(
    navController: NavHostController,
    onError: (message: String) -> Unit
) {
    composable<ResetPassword> {
        ResetPasswordScreen(
            onMessage = onError,
            onNavigateToLogin = { navController.navigateToLoginScreen() }
        )
    }
}

@Serializable
data object ResetPassword