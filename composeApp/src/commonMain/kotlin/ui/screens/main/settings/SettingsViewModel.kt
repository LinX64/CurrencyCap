package ui.screens.main.settings

import androidx.lifecycle.viewModelScope
import domain.repository.UserPreferences
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.settings.SettingsState.IsDarkMode
import ui.screens.main.settings.SettingsViewEvent.OnDarkModeSwitchChange
import ui.screens.main.settings.SettingsViewEvent.OnGetThemeSettings
import ui.screens.main.settings.SettingsViewEvent.OnPushNotificationSwitchChange

internal class SettingsViewModel(
    private val userPreferences: UserPreferences
) : MviViewModel<SettingsViewEvent, SettingsState, SettingsNavigationEffect>(SettingsState.Idle) {

    init {
        handleEvent(OnGetThemeSettings)
    }

    override fun handleEvent(event: SettingsViewEvent) {
        when (event) {
            OnGetThemeSettings -> onGetThemeSettings()
            is OnDarkModeSwitchChange -> onDarkModeSwitchChange(event.isDarkMode)
            is OnPushNotificationSwitchChange -> onPushNotificationSwitchChange(event.isPushNotificationEnabled)
        }
    }

    private fun onGetThemeSettings() {
        viewModelScope.launch {
            when (val isDark = userPreferences.isDarkMode()) {
                true -> setState { IsDarkMode(isDark) }
                false -> setState { IsDarkMode(false) }
            }
        }
    }

    private fun onDarkModeSwitchChange(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferences.setDarkMode(isDarkMode)
        }
    }

    private fun onPushNotificationSwitchChange(isPushNotificationEnabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setPushNotificationEnabled(isPushNotificationEnabled)
        }
    }
}