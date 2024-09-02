package ui.screens.main.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.settings.SettingsNavigationEffect.OpenBrowser
import ui.screens.main.settings.SettingsNavigationEffect.ShowAboutUsBottomSheet
import ui.screens.main.settings.SettingsState.Idle
import ui.screens.main.settings.SettingsViewEvent.OnAboutUsClick
import ui.screens.main.settings.SettingsViewEvent.OnDarkModeSwitchChange
import ui.screens.main.settings.SettingsViewEvent.OnGetPushNotificationSettings
import ui.screens.main.settings.SettingsViewEvent.OnGetThemeSettings
import ui.screens.main.settings.SettingsViewEvent.OnPrivacyPolicyClick
import ui.screens.main.settings.SettingsViewEvent.OnPushNotificationSwitchChange

internal class SettingsViewModel(
    private val userPreferences: UserPreferences,
) : MviViewModel<SettingsViewEvent, SettingsState, SettingsNavigationEffect>(Idle) {

    var isDarkMode by mutableStateOf(true)
    var isPushNotificationEnabled by mutableStateOf(false)

    init {
        handleEvent(OnGetThemeSettings)
        handleEvent(OnGetPushNotificationSettings)
    }

    override fun handleEvent(event: SettingsViewEvent) {
        when (event) {
            OnAboutUsClick -> setEffect(ShowAboutUsBottomSheet)
            OnPrivacyPolicyClick -> setEffect(OpenBrowser("https://currency-cap.web.app/privacy-policy.html"))
            OnGetThemeSettings -> onGetThemeSettings()
            OnGetPushNotificationSettings -> onGetPushNotificationSettings()
            is OnDarkModeSwitchChange -> onDarkModeSwitchChange(event.isDarkMode)
            is OnPushNotificationSwitchChange -> onPushNotificationSwitchChange(event.isEnabled)
        }
    }

    private fun onGetThemeSettings() {
        userPreferences.isDarkMode()
            .map {
                isDarkMode = it
            }
            .launchIn(viewModelScope)
    }

    private fun onGetPushNotificationSettings() {
        userPreferences.isPushNotificationEnabled()
            .map {
                isPushNotificationEnabled = it
            }
            .launchIn(viewModelScope)
    }

    private fun onDarkModeSwitchChange(isDarkMode: Boolean = true) {
        viewModelScope.launch {
            userPreferences.setDarkMode(isDarkMode)
        }
    }

    private fun onPushNotificationSwitchChange(isEnabled: Boolean = false) {
        viewModelScope.launch {
            userPreferences.setPushNotificationEnabled(isEnabled)
        }
    }
}