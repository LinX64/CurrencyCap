package com.client.currencycap.main

import androidx.lifecycle.viewModelScope
import data.repository.datastore.user.UserPreferences
import kotlinx.coroutines.launch
import ui.common.MviViewModel

class MainViewModel(
    private val userPreferences: UserPreferences
) : MviViewModel<MainViewEvent, MainState, MainNavigationEffect>(MainState.Idle) {

    init {
        checkUserLoginStatus()
    }

    override fun handleEvent(event: MainViewEvent) {}

    private fun checkUserLoginStatus() {
        viewModelScope.launch {
            val userLoggedIn = userPreferences.isUserLoggedIn()
            if (userLoggedIn) {
                val uid = userPreferences.getUserUid()
                setEffect(MainNavigationEffect.NavigateToOverview(uid))
            } else {
                setEffect(MainNavigationEffect.NavigateToLogin)
            }
        }
    }
}
