package ui.screens.auth.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import domain.repository.AuthService
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.register.RegisterState.Loading
import ui.screens.auth.register.RegisterViewEvent.OnEmailChanged
import ui.screens.auth.register.RegisterViewEvent.OnRegisterClick

internal class RegisterViewModel(
    private val authService: AuthService
) : MviViewModel<RegisterViewEvent, RegisterState, RegisterNavigationEffect>(Loading) {

    val email: MutableState<String> = mutableStateOf("")

    override fun handleEvent(event: RegisterViewEvent) {
        when (event) {
            is OnEmailChanged -> onEmailChanged(event.newEmail)
            is OnRegisterClick -> register(email.value)
        }
    }

    private fun onEmailChanged(newEmail: String) {
        email.value = newEmail
    }

    private fun register(email: String) {
        if (email.isEmpty()) {
            setEffect(RegisterNavigationEffect.RegisterError("Email is required"))
            return
        }

        viewModelScope.launch {
            authService.signUpWithEmailOnly(email)
        }
    }
}