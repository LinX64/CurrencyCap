package ui.screens.auth.login

sealed interface LoginViewEvent {
    data class OnEmailChanged(val email: String) : LoginViewEvent
    data class OnPasswordChanged(val password: String) : LoginViewEvent

    data class OnLoginClick(
        val email: String,
        val password: String
    ) : LoginViewEvent

    data object OnErrorDialogDismissed : LoginViewEvent
}

sealed interface LoginState {
    data object Loading : LoginState
    data class Success(val uid: String) : LoginState
    data class Error(val message: String) : LoginState
}

sealed interface LoginNavigationEffect {
    data class LoginSuccess(val uid: String) : LoginNavigationEffect
}

