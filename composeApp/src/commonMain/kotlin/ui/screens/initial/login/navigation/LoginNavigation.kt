package ui.screens.initial.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.util.NavRoutes
import ui.screens.initial.login.LoginScreen
import ui.screens.initial.register.navigation.navigateToRegisterScreen
import ui.screens.initial.reset_password.navigation.navigateToResetPassword

fun NavController.navigateToLoginScreen() = navigate(NavRoutes.LOGIN)

fun NavGraphBuilder.loginScreen(
    padding: PaddingValues,
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.LOGIN) {
        LoginScreen(
            padding = padding,
            onError = onError,
            navigateToMarketOverview = onLoginSuccess,
            navigateToRegister = { navController.navigateToRegisterScreen() },
            navigateToResetPassword = { navController.navigateToResetPassword() }
        )
    }
}
