package ui.screens.settings

import domain.repository.AuthServiceRepository
import domain.repository.UserPreferences
import ui.common.MviViewModel

internal class SettingsViewModel(
    private val authService: AuthServiceRepository,
    private val userPreferences: UserPreferences
) : MviViewModel<SettingsViewEvent, SettingsState, SettingsNavigationEffect>(SettingsState.Idle) {

    override fun handleEvent(event: SettingsViewEvent) {

    }
}