package ui.screens.settings

import data.remote.model.User

sealed interface SettingsViewEvent {
}

sealed interface SettingsState {
    data object Idle : SettingsState
    data object Loading : SettingsState
    data class Success(val user: User) : SettingsState
    data class Error(val message: String) : SettingsState
}

sealed interface SettingsNavigationEffect {
    data object NavigateToLanding : SettingsNavigationEffect
}

