package ui.screens.auth.login

sealed interface LoginViewEvent {
    data object OnLoginClick : LoginViewEvent
}

sealed interface LoginState {
    data object Loading : LoginState
}

sealed interface LoginNavigationEffect {
    data object NavigateToHome : LoginNavigationEffect
}