package ui.screens.auth.login

sealed interface LoginViewEvent {
    data class OnEmailChanged(val email: String) : LoginViewEvent
    data class OnPasswordChanged(val password: String) : LoginViewEvent
    data object OnResetPasswordClick : LoginViewEvent

    data class OnLoginClick(
        val email: String,
        val password: String
    ) : LoginViewEvent

    data object OnSignUpClick : LoginViewEvent
}

sealed interface LoginState {
    data object Idle : LoginState
    data object Loading : LoginState
    data class Success(val uid: String) : LoginState
    data class Error(val message: String) : LoginState
}

sealed interface LoginNavigationEffect {
    data object NavigateToMarketOverview : LoginNavigationEffect
    data object NavigateToRegister : LoginNavigationEffect
    data object NavigateToResetPassword : LoginNavigationEffect
}

