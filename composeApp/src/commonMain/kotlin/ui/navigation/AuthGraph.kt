package ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ui.screens.auth.login.navigation.loginScreen
import ui.screens.auth.register.navigation.registerScreen

internal fun NavGraphBuilder.authGraph(
    padding: PaddingValues,
    navController: NavHostController,
    onError: (message: String) -> Unit
) {
    loginScreen(
        padding = padding,
        navController = navController,
        onError = onError
    )
    registerScreen(
        padding = padding,
        navController = navController,
        onError = onError
    )
}