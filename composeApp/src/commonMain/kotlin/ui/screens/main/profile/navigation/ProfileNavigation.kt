package ui.screens.main.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.Screen.Profile
import ui.screens.main.profile.ProfileRoute

fun NavController.navigateToProfileScreen(navOptions: NavOptions) = navigate(Profile, navOptions)

fun NavGraphBuilder.profileScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState
) {
    composable<Profile> {
        ProfileRoute(
            padding = padding,
            onNavigateToLanding = onNavigateToLanding,
            onError = onError,
            hazeState = hazeState
        )
    }
}
