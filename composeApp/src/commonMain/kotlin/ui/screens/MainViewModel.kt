package ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.datastore.user.UserPreferences
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
            val userLoggedIn = userPreferences.isUserLoggedIn()
            if (userLoggedIn) {
                val uid = userPreferences.getUserUid()
                _state.value = MainState.LoggedIn(uid)
            } else _state.value = MainState.NotLoggedIn
        }
    }
}

sealed class MainState {
    data object Idle : MainState()
    data object Loading : MainState()
    data object NotLoggedIn : MainState()
    data class LoggedIn(val uid: String) : MainState()
}