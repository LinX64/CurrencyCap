package ui.screens.auth.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.auth.register.RegisterScreen

fun NavController.navigateToRegisterScreen() = navigate(NavRoutes.REGISTER)

fun NavGraphBuilder.registerScreen(padding: PaddingValues) {
    composable(NavRoutes.REGISTER) {
        BlurBackground {
            RegisterScreen(padding)
        }
    }
}
