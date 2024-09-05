package ui.screens.main

sealed interface MainViewEvent {
    data object OnGetUserStatus : MainViewEvent
}

sealed interface MainState {
    data object Idle : MainState
    data object Loading : MainState
    data object LoggedIn : MainState
    data object NotLoggedIn : MainState
}

sealed interface MainNavigationEffect {
    data object NavigateToLogin : MainNavigationEffect
}