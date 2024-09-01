package ui.screens.main.settings

import data.remote.model.User

sealed interface SettingsViewEvent {
    data object OnAboutUsClick : SettingsViewEvent
    data object OnPrivacyPolicyClick : SettingsViewEvent
    data object OnGetThemeSettings : SettingsViewEvent
    data class OnDarkModeSwitchChange(val isDarkMode: Boolean) : SettingsViewEvent
    data class OnPushNotificationSwitchChange(val isEnabled: Boolean) : SettingsViewEvent
}

sealed interface SettingsState {
    data object Idle : SettingsState
    data object Loading : SettingsState
    data class IsDarkMode(val isDarkMode: Boolean) : SettingsState
    data class Success(val user: User) : SettingsState
    data class Error(val message: String) : SettingsState
}

sealed interface SettingsNavigationEffect {
    data class OpenBrowser(val link: String) : SettingsNavigationEffect
    data object ShowAboutUsBottomSheet : SettingsNavigationEffect
}

