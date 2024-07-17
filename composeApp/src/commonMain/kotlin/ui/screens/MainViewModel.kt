package ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.theme.ThemeMode

class MainViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state = _state.asStateFlow()
    val isDarkMode = mutableStateOf(ThemeMode.SYSTEM)

    init {
        checkUserLoginStatus()
        checkDarkModeStatus()
    }

    private fun checkDarkModeStatus() {
        viewModelScope.launch {
            when (userPreferences.isDarkMode()) {
                true -> isDarkMode.value = ThemeMode.DARK
                false -> isDarkMode.value = ThemeMode.LIGHT
            }
        }
    }

    private fun checkUserLoginStatus() {
        _state.value = MainState.Loading

        viewModelScope.launch {
            delay(1500)

            val userLoggedIn = userPreferences.isUserLoggedIn()
            if (userLoggedIn) {
                val uid = userPreferences.getUserUid()
                _state.value = MainState.LoggedIn(uid)
            } else _state.value = MainState.NotLoggedIn
        }
    }

    fun onLoginSuccess() {
        viewModelScope.launch {
            val uid = userPreferences.getUserUid()
            _state.value = MainState.LoggedIn(uid)
        }
    }

    fun navigateToLanding() {
        _state.value = MainState.NotLoggedIn
    }
}

sealed class MainState {
    data object Idle : MainState()
    data object Loading : MainState()
    data object NotLoggedIn : MainState()
    data class LoggedIn(val uid: String) : MainState()
}