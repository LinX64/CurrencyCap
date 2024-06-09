package ui.screens.auth.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.auth.login.LoginScreen

fun NavController.navigateToLoginScreen() = navigate(NavRoutes.LOGIN)

fun NavGraphBuilder.loginScreen(padding: PaddingValues) {
    composable(NavRoutes.LOGIN) {
        BlurBackground {
            LoginScreen(padding)
        }
    }
}
