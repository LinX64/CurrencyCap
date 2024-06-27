package ui.screens.settings

import domain.repository.AuthService
import domain.repository.UserPreferences
import ui.common.MviViewModel

internal class SettingsViewModel(
    private val authService: AuthService,
    private val userPreferences: UserPreferences
) : MviViewModel<SettingsViewEvent, SettingsState, SettingsNavigationEffect>(SettingsState.Idle) {

    override fun handleEvent(event: SettingsViewEvent) {

    }
}