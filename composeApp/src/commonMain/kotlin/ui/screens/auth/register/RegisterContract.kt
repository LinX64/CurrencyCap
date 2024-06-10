package ui.screens.auth.register

sealed interface RegisterViewEvent {
    data class OnNameChanged(val name: String) : RegisterViewEvent
    data class OnLastNameChanged(val lastName: String) : RegisterViewEvent
    data class OnEmailChanged(val email: String) : RegisterViewEvent
    data class OnPasswordChanged(val password: String) : RegisterViewEvent

    data class OnRegisterClick(
        val name: String,
        val lastName: String,
        val email: String,
        val password: String
    ) : RegisterViewEvent
}

sealed interface RegisterState {
    data object Loading : RegisterState

    data class Content(
        val name: String = "",
        val lastName: String = "",
        val email: String = "",
        val password: String = ""
    ) : RegisterState

    data class Success(val uid: String) : RegisterState
    data class Error(val message: String) : RegisterState
}

sealed interface RegisterNavigationEffect {
    data object RegisterSuccess : RegisterNavigationEffect
    data class RegisterError(val message: String) : RegisterNavigationEffect
}

