package ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state = _state.asStateFlow()

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        _state.value = MainState.Loading

        viewModelScope.launch {
            // delay(1500) TODO: Revert this change

            val userLoggedIn = true
            if (userLoggedIn) {
                val uid = userPreferences.getUserUid()
                _state.value = MainState.LoggedIn(uid)
            } else _state.value = MainState.NotLoggedIn
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clear()
            _state.value = MainState.NotLoggedIn
        }
    }

    fun onLoginSuccess() {
        viewModelScope.launch {
            val uid = userPreferences.getUserUid()
            _state.value = MainState.LoggedIn(uid)
        }
    }
}

sealed class MainState {
    data object Idle : MainState()
    data object Loading : MainState()
    data object NotLoggedIn : MainState()
    data class LoggedIn(val uid: String) : MainState()
}