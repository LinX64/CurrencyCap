package ui.screens.initial.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ui.screens.initial.login.LoginRoute
import ui.screens.initial.register.navigation.navigateToRegisterScreen
import ui.screens.initial.reset_password.navigation.navigateToResetPassword

fun NavController.navigateToLoginScreen() = navigate(Login)

fun NavGraphBuilder.loginScreen(
    padding: PaddingValues,
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable<Login> {
        LoginRoute(
            padding = padding,
            onError = onError,
            navigateToMarketOverview = onLoginSuccess,
            navigateToRegister = { navController.navigateToRegisterScreen() },
            navigateToResetPassword = { navController.navigateToResetPassword() }
        )
    }
}

@Serializable
data object Login