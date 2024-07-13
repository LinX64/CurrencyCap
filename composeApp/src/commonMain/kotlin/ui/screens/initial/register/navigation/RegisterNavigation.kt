package ui.screens.initial.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.util.Screen.Register
import ui.screens.initial.fill_profile.navigation.navigateToFillProfileScreen
import ui.screens.initial.login.navigation.navigateToLoginScreen
import ui.screens.initial.register.RegisterScreen

fun NavController.navigateToRegisterScreen() = navigate(Register)

fun NavGraphBuilder.registerScreen(
    navController: NavHostController,
    onError: (message: String) -> Unit
) {
    composable<Register> {
        RegisterScreen(
            onNavigateToFillProfile = { navController.navigateToFillProfileScreen() },
            navigateToLogin = { navController.navigateToLoginScreen() },
            onError = onError
        )
    }
}
