package ui.screens.auth.reset_password

sealed interface ResetPasswordViewEvent {
    data object OnResetPasswordClick : ResetPasswordViewEvent
    data class OnEmailChanged(val email: String) : ResetPasswordViewEvent
}

sealed interface ResetPasswordState {
    data object Idle : ResetPasswordState
    data class Error(val message: String) : ResetPasswordState
    data object Success : ResetPasswordState
}

sealed interface ResetPasswordNavigationEffect