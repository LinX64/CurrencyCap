package ui.screens.main.settings.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.settings.SettingsRoute

fun NavController.navigateToSettingsScreen() = navigate(Settings)

fun NavGraphBuilder.settingsScreen(
    padding: PaddingValues,
    hazeState: HazeState
) {
    composable<Settings> {
        SettingsRoute(
            padding = padding,
            hazeState = hazeState
        )
    }
}

@Serializable
data object Settings