package ui.screens.auth.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.auth.forgot_password.navigation.navigateToResetPassword
import ui.screens.auth.login.LoginScreen
import ui.screens.auth.register.navigation.navigateToRegisterScreen

fun NavController.navigateToLoginScreen() = navigate(NavRoutes.LOGIN)

fun NavGraphBuilder.loginScreen(
    padding: PaddingValues,
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.LOGIN) {
        BlurBackground {
            LoginScreen(
                padding = padding,
                onError = onError,
                navigateToMarketOverview = onLoginSuccess,
                navigateToRegister = { navController.navigateToRegisterScreen() },
                navigateToResetPassword = { navController.navigateToResetPassword() }
            )
        }
    }
}
