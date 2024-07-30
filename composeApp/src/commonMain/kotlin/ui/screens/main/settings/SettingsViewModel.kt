package ui.screens.main.settings

import androidx.lifecycle.viewModelScope
import com.mvicompose.linx64.ui.MviViewModel
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui.screens.main.settings.SettingsViewEvent.OnDarkModeSwitchChange
import ui.screens.main.settings.SettingsViewEvent.OnGetThemeSettings
import ui.screens.main.settings.SettingsViewEvent.OnPushNotificationSwitchChange
import ui.theme.ThemeMode

internal class SettingsViewModel(
    private val userPreferences: UserPreferences
) : MviViewModel<SettingsViewEvent, SettingsState, SettingsNavigationEffect>(SettingsState.Idle) {

    val isDarkMode = userPreferences.isDarkMode()
        .map { isDarkMode -> if (isDarkMode) ThemeMode.DARK else ThemeMode.LIGHT }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ThemeMode.SYSTEM
        )

    init {
        handleEvent(OnGetThemeSettings)
    }

    override fun handleEvent(event: SettingsViewEvent) {
        when (event) {
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

    private fun onDarkModeSwitchChange(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferences.setDarkMode(isDarkMode)
        }
    }

    private fun onPushNotificationSwitchChange(isEnabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setPushNotificationEnabled(isEnabled)
        }
    }
}