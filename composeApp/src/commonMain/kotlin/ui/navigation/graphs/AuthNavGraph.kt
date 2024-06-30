package ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ui.navigation.util.NavRoutes
import ui.screens.initial.fill_profile.navigation.fillProfileScreen
import ui.screens.initial.get_verified.navigation.getVerifiedPhoneScreen
import ui.screens.initial.landing.navigation.landingScreen
import ui.screens.initial.login.navigation.loginScreen
import ui.screens.initial.register.navigation.registerScreen
import ui.screens.initial.reset_password.navigation.resetPasswordScreen

@Composable
internal fun AuthNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LANDING
    ) {
        landingScreen(navController)

        loginScreen(
            padding = padding,
            navController = navController,
            onLoginSuccess = onLoginSuccess,
            onError = onError
        )

        registerScreen(
            navController = navController,
            onError = onError
        )

        fillProfileScreen(
            padding = padding,
            onNavigateToMarketOverview = onLoginSuccess,
            onError = onError
        )

        getVerifiedPhoneScreen(
            padding = padding,
            onNavigateToMarketOverview = onLoginSuccess,
            onError = onError
        )

        resetPasswordScreen(
            navController = navController,
            padding = padding,
            onError = onError
        )
    }
}