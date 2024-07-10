package ui.screens.main.settings.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.Screens.Settings
import ui.navigation.util.NavRoutes
import ui.screens.main.settings.SettingsScreen

fun NavController.navigateToSettingsScreen() = navigate(NavRoutes.SETTINGS)

fun NavGraphBuilder.settingsScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState
) {
    composable<Settings> {
        SettingsScreen(
            padding = padding,
            onNavigateToLanding = onNavigateToLanding,
            onError = onError,
            hazeState = hazeState
        )
    }
}
