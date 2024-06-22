package com.client.currencycap.main

sealed interface MainViewEvent {
    data class OnUserLoggedIn(val userLoggedIn: Boolean) : MainViewEvent
}

sealed interface MainState {
    data object Loading : MainState
    data object UserNotLoggedIn : MainState
    data class Success(val userLoggedIn: Boolean) : MainState
}

sealed interface MainNavigationEffect {
    data class NavigateToOverview(val userId: String) : MainNavigationEffect
    object NavigateToLogin : MainNavigationEffect
}