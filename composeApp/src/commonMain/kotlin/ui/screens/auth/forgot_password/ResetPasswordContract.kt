package ui.screens.auth.forgot_password

sealed interface ResetPasswordViewEvent {
    data class OnEmailChanged(val email: String) : ResetPasswordViewEvent
    data object OnResetPasswordClick : ResetPasswordViewEvent
}

sealed interface ResetPasswordState {
    data object Idle : ResetPasswordState
    data class Error(val message: String) : ResetPasswordState
}

sealed interface ResetPasswordNavigationEffect {
    data object NavigateToLogin : ResetPasswordNavigationEffect
}

