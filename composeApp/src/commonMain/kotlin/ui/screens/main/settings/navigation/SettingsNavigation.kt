package ui.screens.main.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.settings.SettingsRoute

fun NavController.navigateToSettingsScreen() = navigate(Settings)

fun NavGraphBuilder.settingsScreen(
    hazeState: HazeState,
    onShowAboutUsBottomSheet: () -> Unit,
    onShowPrivacyPolicy: () -> Unit
) {
    composable<Settings> {
        SettingsRoute(
            hazeState = hazeState,
            onShowAboutUsBottomSheet = onShowAboutUsBottomSheet,
            onShowPrivacyPolicy = onShowPrivacyPolicy
        )
    }
}

@Serializable
data object Settings