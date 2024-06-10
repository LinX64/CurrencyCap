package ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import data.repository.auth.AuthServiceImpl.AuthResponse
import domain.repository.AuthService
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.login.LoginState.Error
import ui.screens.auth.login.LoginState.Loading
import ui.screens.auth.login.LoginState.Success
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.LoginViewEvent.OnLoginClick
import ui.screens.auth.login.LoginViewEvent.OnPasswordChanged

internal class LoginViewModel(
    private val authService: AuthService
) : MviViewModel<LoginViewEvent, LoginState, LoginNavigationEffect>(Loading) {

    override fun handleEvent(event: LoginViewEvent) {
        when (event) {
            is OnLoginClick -> authenticate(email = event.email, password = event.password)
            is OnEmailChanged -> setState { LoginState.Content(email = event.email) }
            is OnPasswordChanged -> setState { LoginState.Content(password = event.password) }
        }
    }

    private fun authenticate(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            setState { Error("Email and password must not be empty") }
            return
        }

        viewModelScope.launch {
            when (val result = authService.authenticate(email, password)) {
                is AuthResponse.Loading -> Loading
                is AuthResponse.Success -> Success(result.uid)
                is AuthResponse.Error -> Error(result.message)
            }
        }
    }
}