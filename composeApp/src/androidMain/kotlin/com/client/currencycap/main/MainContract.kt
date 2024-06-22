package com.client.currencycap.main

sealed interface MainViewEvent

sealed interface MainState {
    data object Idle : MainState
    data class LoggedIn(val uid: String) : MainState
    data object LoggedOut : MainState
}

sealed interface MainNavigationEffect {
    data class NavigateToOverview(val userId: String) : MainNavigationEffect
    data object NavigateToLogin : MainNavigationEffect
}