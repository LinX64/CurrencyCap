package ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import data.repository.auth.AuthServiceImpl.AuthResponse
import domain.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.login.LoginState.Error
import ui.screens.auth.login.LoginState.Loading
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.LoginViewEvent.OnLoginClick
import ui.screens.auth.login.LoginViewEvent.OnPasswordChanged
import util.validateEmail

internal class LoginViewModel(
    private val authService: AuthService
) : MviViewModel<LoginViewEvent, LoginState, LoginNavigationEffect>(LoginState.Idle) {

    val newEmail: MutableStateFlow<String> = MutableStateFlow("")
    val newPassword: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: LoginViewEvent) {
        when (event) {
            is OnLoginClick -> authenticate(email = event.email, password = event.password)
            is OnEmailChanged -> newEmail.value = event.email
            is OnPasswordChanged -> newPassword.value = event.password
            LoginViewEvent.OnErrorDialogDismissed -> setState { LoginState.Idle }
        }
    }

    private fun authenticate(email: String, password: String) {
        if (email.isEmpty()) {
            setState { Error("Email must not be empty") }
            return
        }

        if (password.isEmpty()) {
            setState { Error("Password must not be empty") }
            return
        }

        if (email.validateEmail().not()) {
            setState { Error("Invalid email format") }
            return
        }

        viewModelScope.launch {
            when (val result = authService.authenticate(email, password)) {
                is AuthResponse.Loading -> setState { Loading }
                is AuthResponse.Success -> setEffect(LoginNavigationEffect.LoginSuccess(result.uid))
                is AuthResponse.Error -> setState { Error(result.message) }
            }
        }
    }
}