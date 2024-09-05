package ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.MainNavigationEffect
import ui.screens.main.MainState
import ui.screens.main.MainState.Loading
import ui.screens.main.MainState.LoggedIn
import ui.screens.main.MainState.NotLoggedIn
import ui.screens.main.MainViewEvent
import ui.screens.main.MainViewEvent.OnGetUserStatus

class MainViewModel(
    private val userPreferences: UserPreferences
) : MviViewModel<MainViewEvent, MainState, MainNavigationEffect>(Loading) {
    var isSubscribeSheetVisible by mutableStateOf(false)
    var isNewsFilterSheetVisible by mutableStateOf(false)
    var isPrivacyPolicySheetVisible by mutableStateOf(false)
    var isAboutUsSheetVisible by mutableStateOf(false)

    val isDark: StateFlow<Boolean> = userPreferences.isDarkMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(800L),
            initialValue = false
        )

    override fun handleEvent(event: MainViewEvent) {
        when (event) {
            is OnGetUserStatus -> getUserStatus()
        }
    }

    private fun getUserStatus() {
        setState { Loading }

        viewModelScope.launch {
            val userLoggedIn = userPreferences.isUserLoggedIn()
            if (userLoggedIn) onLoginSuccess() else updateStateToNotLoggedIn()
        }
    }

    fun onLoginSuccess() {
        setState { LoggedIn }
    }

    fun updateStateToNotLoggedIn() {
        setState { NotLoggedIn }
    }

    fun toggleDarkMode(isDark: Boolean = false) {
        viewModelScope.launch {
            userPreferences.setDarkMode(isDark)
        }
    }

    fun toggleSheet(sheetType: SheetType) {
        when (sheetType) {
            SheetType.SUBSCRIBE -> isSubscribeSheetVisible = !isSubscribeSheetVisible
            SheetType.NEWS_FILTER -> isNewsFilterSheetVisible = !isNewsFilterSheetVisible
            SheetType.PRIVACY_POLICY -> isPrivacyPolicySheetVisible = !isPrivacyPolicySheetVisible
            SheetType.ABOUT_US -> isAboutUsSheetVisible = !isAboutUsSheetVisible
        }
    }
}

enum class SheetType {
    SUBSCRIBE,
    NEWS_FILTER,
    PRIVACY_POLICY,
    ABOUT_US
}