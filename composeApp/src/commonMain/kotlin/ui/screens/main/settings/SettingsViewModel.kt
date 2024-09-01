package ui.screens.main.settings

import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.settings.SettingsNavigationEffect.OpenBrowser
import ui.screens.main.settings.SettingsNavigationEffect.ShowAboutUsBottomSheet
import ui.screens.main.settings.SettingsNavigationEffect.ShowDeniedPermissions
import ui.screens.main.settings.SettingsNavigationEffect.ShowFailedToGetPermission
import ui.screens.main.settings.SettingsViewEvent.OnAboutUsClick
import ui.screens.main.settings.SettingsViewEvent.OnDarkModeSwitchChange
import ui.screens.main.settings.SettingsViewEvent.OnGetThemeSettings
import ui.screens.main.settings.SettingsViewEvent.OnPrivacyPolicyClick
import ui.screens.main.settings.SettingsViewEvent.OnPushNotificationSwitchChange

internal class SettingsViewModel(
    private val userPreferences: UserPreferences,
    private val permissionsController: PermissionsController
) : MviViewModel<SettingsViewEvent, SettingsState, SettingsNavigationEffect>(SettingsState.Idle) {

    init {
        handleEvent(OnGetThemeSettings)
    }

    override fun handleEvent(event: SettingsViewEvent) {
        when (event) {
            OnAboutUsClick -> setEffect(ShowAboutUsBottomSheet)
            OnPrivacyPolicyClick -> setEffect(OpenBrowser("https://currency-cap.web.app/privacy-policy.html"))
            OnGetThemeSettings -> onGetThemeSettings()
            is OnDarkModeSwitchChange -> onDarkModeSwitchChange(event.isDarkMode)
            is OnPushNotificationSwitchChange -> onPushNotificationSwitchChange(event.isEnabled)
        }
    }

    private fun onGetThemeSettings() {
        userPreferences.isDarkMode()
            .map { isDarkMode ->
                if (isDarkMode) setState { SettingsState.IsDarkMode(true) }
                else setState { SettingsState.IsDarkMode(false) }
            }
            .launchIn(viewModelScope)
    }

    private fun onDarkModeSwitchChange(isDarkMode: Boolean = false) {
        viewModelScope.launch {
            userPreferences.setDarkMode(isDarkMode)
        }
    }

    private fun onPushNotificationSwitchChange(isEnabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setPushNotificationEnabled(isEnabled)

            try {
                permissionsController.providePermission(Permission.REMOTE_NOTIFICATION)
            } catch (deniedAlways: DeniedAlwaysException) {
                setEffect(ShowDeniedPermissions)
            } catch (denied: DeniedException) {
                setEffect(ShowFailedToGetPermission)
            }
        }
    }
}