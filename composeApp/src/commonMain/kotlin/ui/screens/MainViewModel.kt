package ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui.screens.MainState.Idle
import ui.screens.MainState.Loading
import ui.screens.MainState.LoggedIn
import ui.screens.MainState.NotLoggedIn

class MainViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(Idle)
    val appState: StateFlow<MainState> = _state.asStateFlow()

    var isSubscribeSheetVisible by mutableStateOf(false)
    var isNewsFilterSheetVisible by mutableStateOf(false)
    var isPrivacyPolicySheetVisible by mutableStateOf(false)

    val isDark: StateFlow<Boolean> = userPreferences.isDarkMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000L),
            initialValue = false
        )

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        _state.value = Loading

        viewModelScope.launch {
            delay(1500)

            val userLoggedIn = userPreferences.isUserLoggedIn()
            if (userLoggedIn) onLoginSuccess() else updateStateToNotLoggedIn()
        }
    }

    fun onLoginSuccess() {
        _state.value = LoggedIn
    }

    fun updateStateToNotLoggedIn() {
        _state.value = NotLoggedIn
    }

    fun toggleDarkMode(isDark: Boolean = false) {
        viewModelScope.launch {
            userPreferences.setDarkMode(isDark)
        }
    }

    fun toggleSubscribeSheet() {
        isSubscribeSheetVisible = !isSubscribeSheetVisible
    }

    fun toggleNewsFilterSheet() {
        isNewsFilterSheetVisible = !isNewsFilterSheetVisible
    }

    fun togglePrivacyPolicySheet() {
        isPrivacyPolicySheetVisible = !isPrivacyPolicySheetVisible
    }
}

sealed class MainState {
    data object Idle : MainState()
    data object Loading : MainState()
    data object NotLoggedIn : MainState()
    data object LoggedIn : MainState()
}