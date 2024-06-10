package ui.screens.auth.register

import androidx.lifecycle.viewModelScope
import data.repository.auth.AuthServiceImpl.AuthResponse
import domain.repository.AuthService
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.register.RegisterState.Error
import ui.screens.auth.register.RegisterState.Loading
import ui.screens.auth.register.RegisterState.Success
import ui.screens.auth.register.RegisterViewEvent.OnEmailChanged
import ui.screens.auth.register.RegisterViewEvent.OnLastNameChanged
import ui.screens.auth.register.RegisterViewEvent.OnNameChanged
import ui.screens.auth.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.auth.register.RegisterViewEvent.OnRegisterClick

internal class RegisterViewModel(
    private val authService: AuthService
) : MviViewModel<RegisterViewEvent, RegisterState, RegisterNavigationEffect>(Loading) {

    override fun handleEvent(event: RegisterViewEvent) {
        when (event) {
            is OnEmailChanged -> setState { RegisterState.Content(event.email) }
            is OnLastNameChanged -> setState { RegisterState.Content(event.lastName) }
            is OnNameChanged -> setState { RegisterState.Content(event.name) }
            is OnPasswordChanged -> setState { RegisterState.Content(event.password) }
            is OnRegisterClick -> register(event.name, event.lastName, event.email, event.password)
        }
    }

    private fun register(
        name: String,
        lastName: String,
        email: String,
        password: String
    ) {
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            setState { Error("Please fill all fields") }
            return
        }

        viewModelScope.launch {
            when (val result = authService.signUpWithEmail(email, password)) {
                is AuthResponse.Loading -> Loading
                is AuthResponse.Success -> Success(result.uid)
                is AuthResponse.Error -> Error(result.message)
            }
        }
    }
}