package ui.screens.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.base.BaseGlassLazyColumn
import ui.components.base.GlassCard
import ui.screens.main.profile.components.HelpCenterItem
import ui.screens.main.settings.SettingsViewEvent.OnDarkModeSwitchChange
import ui.screens.main.settings.SettingsViewEvent.OnPushNotificationSwitchChange
import ui.screens.main.settings.components.SettingsGeneralItem
import ui.screens.main.settings.components.SettingsHeaderText
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun SettingsRoute(
    padding: PaddingValues,
    settingsViewModel: SettingsViewModel = koinViewModel<SettingsViewModel>(),
    hazeState: HazeState,
) {
    val state by settingsViewModel.viewState.collectAsStateWithLifecycle()

    SettingsScreen(
        padding = padding,
        hazeState = hazeState,
        state = state,
        onPushNotificationSwitchChange = { settingsViewModel.handleEvent(OnPushNotificationSwitchChange(it)) },
        onDarkModeSwitchChange = { settingsViewModel.handleEvent(OnDarkModeSwitchChange(it)) },
        onAboutUsClick = { /* Handle about us click */ },
        onPrivacyPolicyClick = { /* Handle privacy policy click */ },
    )
}

@Composable
internal fun SettingsScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    state: SettingsState,
    onPushNotificationSwitchChange: (Boolean) -> Unit,
    onDarkModeSwitchChange: (Boolean) -> Unit,
    onAboutUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState
    ) {
        item {
            GeneralCard(
                isDarkMode = state is SettingsState.IsDarkMode,
                onPushNotificationSwitchChange = onPushNotificationSwitchChange,
                onDarkModeSwitchChange = onDarkModeSwitchChange,
            )
        }
        item {
            PoliciesCard(
                onAboutUsClick = onAboutUsClick,
                onPrivacyPolicyClick = onPrivacyPolicyClick,
            )
        }
    }
}

@Composable
private fun GeneralCard(
    isDarkMode: Boolean,
    onPushNotificationSwitchChange: (Boolean) -> Unit,
    onDarkModeSwitchChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
    ) {
        GlassCard {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                SettingsHeaderText("General")
                Column(modifier = Modifier.fillMaxWidth()) {
                    SettingsGeneralItem(text = "Push Notifications", onSwitchChange = onPushNotificationSwitchChange)

                    SettingsGeneralItem(
                        text = "Dark Mode",
                        isChecked = isDarkMode,
                        onSwitchChange = onDarkModeSwitchChange
                    )
                }
            }
        }
    }
}

@Composable
internal fun PoliciesCard(
    onAboutUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
    ) {
        GlassCard {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                SettingsHeaderText("Policies")

                Spacer(modifier = Modifier.height(SPACER_PADDING_8))

                Column(modifier = Modifier.fillMaxWidth()) {
                    HelpCenterItem(text = "About Us", onButtonClick = onAboutUsClick)

                    Spacer(modifier = Modifier.height(SPACER_PADDING_8))

                    HelpCenterItem(text = "Privacy Policy", onButtonClick = onPrivacyPolicyClick)
                }
            }
        }
    }
}

