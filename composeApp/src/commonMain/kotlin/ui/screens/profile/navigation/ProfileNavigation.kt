package ui.screens.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.profile.ProfileScreen

fun NavController.navigateToProfileScreen() = navigate(NavRoutes.PROFILE)

fun NavGraphBuilder.profileScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.PROFILE) {
        BlurBackground {
            ProfileScreen(
                padding = padding,
                onNavigateToLanding = onNavigateToLanding,
                onError = onError
            )
        }
    }
}
