package ui.screens.main.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.profile.ProfileRoute

fun NavController.navigateToProfileScreen(navOptions: NavOptions) = navigate(Profile, navOptions)

fun NavGraphBuilder.profileScreen(
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState
) {
    composable<Profile> {
        ProfileRoute(
            onNavigateToLanding = onNavigateToLanding,
            onError = onError,
            hazeState = hazeState
        )
    }
}

@Serializable
data object Profile