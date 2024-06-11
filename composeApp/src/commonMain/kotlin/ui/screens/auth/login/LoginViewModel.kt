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

internal class LoginViewModel(
    private val authService: AuthService
) : MviViewModel<LoginViewEvent, LoginState, LoginNavigationEffect>(Loading) {

    val newEmail: MutableStateFlow<String> = MutableStateFlow("")
    val newPassword: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: LoginViewEvent) {
        when (event) {
            is OnLoginClick -> authenticate(email = event.email, password = event.password)
            is OnEmailChanged -> newEmail.value = event.email
            is OnPasswordChanged -> newPassword.value = event.password
            LoginViewEvent.OnErrorDialogDismissed -> TODO()
        }
    }

    private fun authenticate(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            setState { Error("Email and password must not be empty") }
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