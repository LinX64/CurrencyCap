package com.client.currencycap.main

import com.client.currencycap.main.MainViewEvent.OnUserLoggedIn
import data.repository.datastore.app.AppPreferences
import data.repository.datastore.user.UserPreferences
import ui.common.MviViewModel

class MainViewModel(
    private val userPreferences: UserPreferences,
    private val appPreferences: AppPreferences
) : MviViewModel<MainViewEvent, MainState, MainNavigationEffect>(MainState.Loading) {

    override fun handleEvent(event: MainViewEvent) {
        when (event) {
            is OnUserLoggedIn -> TODO()
        }
    }

//    private fun isUserLoggedIn(): Boolean {
//        viewModelScope.launch {
//            val userLoggedIn = userPreferences.isUserLoggedIn()
//            if (userLoggedIn) {
//                setEffect(NavigateToOverview(userPreferences.getUserUid()))
//            } else {
//                setEffect(NavigateToLogin)
//            }
//        }
//    }
}
