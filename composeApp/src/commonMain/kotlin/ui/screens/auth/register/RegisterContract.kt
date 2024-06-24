package ui.screens.auth.register

sealed interface RegisterViewEvent {
    data class OnEmailChanged(val newEmail: String) : RegisterViewEvent
    data class OnPasswordChanged(val newPassword: String) : RegisterViewEvent
    data object OnRegisterClick : RegisterViewEvent
}

sealed interface RegisterState {
    object Idle : RegisterState
    data object Loading : RegisterState
    data class Success(val uid: String) : RegisterState
    data class Error(val message: String) : RegisterState
}

sealed interface RegisterNavigationEffect {
    data object NavigateToMarketOverview : RegisterNavigationEffect
}

