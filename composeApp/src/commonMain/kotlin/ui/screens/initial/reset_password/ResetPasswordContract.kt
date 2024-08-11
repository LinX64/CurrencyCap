package ui.screens.initial.reset_password

sealed interface ResetPasswordViewEvent {
    data object OnResetPasswordClick : ResetPasswordViewEvent
    data class OnEmailChanged(val email: String) : ResetPasswordViewEvent
}

sealed interface ResetPasswordState {
    data object Idle : ResetPasswordState
    data object Loading : ResetPasswordState
    data class Error(val message: String) : ResetPasswordState
    data object Success : ResetPasswordState
}

sealed interface ResetPasswordNavigationEffect {
    data class ShowError(val message: String) : ResetPasswordNavigationEffect
}