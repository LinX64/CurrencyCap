package ui.screens.initial.landing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ui.screens.initial.landing.LandingScreen
import ui.screens.initial.login.navigation.navigateToLoginScreen
import ui.screens.initial.register.navigation.navigateToRegisterScreen

fun NavGraphBuilder.landingScreen(
    navController: NavHostController,
    showPrivacyPolicyBottomSheet: () -> Unit
) {
    composable<Landing> {
        LandingScreen(
            onLoginClick = { navController.navigateToLoginScreen() },
            onSignUpClick = { navController.navigateToRegisterScreen() },
            onPrivacyPolicyClick = showPrivacyPolicyBottomSheet
        )
    }
}

@Serializable
data object Landing