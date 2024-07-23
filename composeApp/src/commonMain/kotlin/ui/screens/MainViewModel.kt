package ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui.screens.MainState.Loading
import ui.screens.MainState.LoggedIn
import ui.screens.MainState.NotLoggedIn
import ui.theme.ThemeMode

class MainViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state = _state.asStateFlow()
    val isDarkMode = userPreferences.isDarkMode()
        .map { isDarkMode -> if (isDarkMode) ThemeMode.DARK else ThemeMode.LIGHT }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ThemeMode.SYSTEM
        )

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        _state.value = Loading

        viewModelScope.launch {
            delay(1500)

            val userLoggedIn = userPreferences.isUserLoggedIn()
            if (userLoggedIn) _state.value = LoggedIn
            else _state.value = NotLoggedIn
        }
    }

    fun onLoginSuccess() {
        viewModelScope.launch {
            _state.value = LoggedIn
        }
    }

    fun navigateToLanding() {
        _state.value = NotLoggedIn
    }
}

sealed class MainState {
    data object Idle : MainState()
    data object Loading : MainState()
    data object NotLoggedIn : MainState()
    data object LoggedIn : MainState()
}