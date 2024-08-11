package ui.screens.initial.register

sealed interface RegisterViewEvent {
    data class OnEmailChanged(val newEmail: String) : RegisterViewEvent
    data class OnPasswordChanged(val newPassword: String) : RegisterViewEvent
    data object OnRegisterClick : RegisterViewEvent
}

sealed interface RegisterState {
    data object Idle : RegisterState
    data object Loading : RegisterState
    data class Success(val uid: String) : RegisterState
    data class Error(val message: String) : RegisterState
}

sealed interface RegisterNavigationEffect {
    data object NavigateToFillProfile : RegisterNavigationEffect
    data class ShowError(val message: String) : RegisterNavigationEffect
}

