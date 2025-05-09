package ui.screens.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.base.BaseGlassLazyColumn
import ui.components.base.GlassCard
import ui.components.base.HandleNavigationEffect
import ui.screens.main.profile.components.HelpCenterItem
import ui.screens.main.settings.SettingsNavigationEffect.OpenBrowser
import ui.screens.main.settings.SettingsNavigationEffect.ShowAboutUsBottomSheet
import ui.screens.main.settings.SettingsNavigationEffect.ShowDeniedPermissions
import ui.screens.main.settings.SettingsViewEvent.OnAboutUsClick
import ui.screens.main.settings.SettingsViewEvent.OnDarkModeSwitchChange
import ui.screens.main.settings.SettingsViewEvent.OnPrivacyPolicyClick
import ui.screens.main.settings.SettingsViewEvent.OnPushNotificationSwitchChange
import ui.screens.main.settings.components.SettingsGeneralItem
import ui.screens.main.settings.components.SettingsHeaderText
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun SettingsRoute(
    settingsViewModel: SettingsViewModel = koinViewModel<SettingsViewModel>(),
    hazeState: HazeState,
    onShowAboutUsBottomSheet: () -> Unit,
    onShowPrivacyPolicy: () -> Unit,
    onError: (String) -> Unit,
) {
    val pushNotificationPermissionState = rememberPermissionState(Permission.Notification)
    SettingsScreen(
        hazeState = hazeState,
        isDarkMode = settingsViewModel.isDarkMode,
        isPushNotificationEnabled = settingsViewModel.isPushNotificationEnabled,
        onPushNotificationSwitchChange = {
            settingsViewModel.handleEvent(OnPushNotificationSwitchChange(it))
            if (it) pushNotificationPermissionState.launchPermissionRequest()
        },
        onDarkModeSwitchChange = { settingsViewModel.handleEvent(OnDarkModeSwitchChange(it)) },
        onAboutUsClick = { settingsViewModel.handleEvent(OnAboutUsClick) },
        onPrivacyPolicyClick = { settingsViewModel.handleEvent(OnPrivacyPolicyClick) },
    )

    HandleNavigationEffect(settingsViewModel) { effect ->
        when (effect) {
            is OpenBrowser -> onShowPrivacyPolicy()
            is ShowAboutUsBottomSheet -> onShowAboutUsBottomSheet()
            is ShowDeniedPermissions -> onError("Push notification permission denied!")
        }
    }
}

@Composable
internal fun SettingsScreen(
    hazeState: HazeState,
    isDarkMode: Boolean = true,
    isPushNotificationEnabled: Boolean = false,
    onPushNotificationSwitchChange: (Boolean) -> Unit,
    onDarkModeSwitchChange: (Boolean) -> Unit,
    onAboutUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    BaseGlassLazyColumn(
        hazeState = hazeState,
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_16),
    ) {
        item {
            GeneralCard(
                isDarkMode = isDarkMode,
                isPushNotificationEnabled = isPushNotificationEnabled,
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
    isDarkMode: Boolean = true,
    isPushNotificationEnabled: Boolean = false,
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
                    SettingsGeneralItem(
                        text = "Push Notifications",
                        isChecked = isPushNotificationEnabled,
                        onSwitchChange = onPushNotificationSwitchChange
                    )

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