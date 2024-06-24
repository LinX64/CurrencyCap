package ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import data.repository.auth.AuthServiceImpl.AuthState
import data.repository.datastore.user.UserPreferences
import domain.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.login.LoginNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.login.LoginNavigationEffect.NavigateToRegister
import ui.screens.auth.login.LoginNavigationEffect.NavigateToResetPassword
import ui.screens.auth.login.LoginState.Error
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.LoginViewEvent.OnLoginClick
import ui.screens.auth.login.LoginViewEvent.OnPasswordChanged
import ui.screens.auth.login.LoginViewEvent.OnResetPasswordClick
import ui.screens.auth.login.LoginViewEvent.OnSignUpClick
import util.validateEmail

internal class LoginViewModel(
    private val authService: AuthService,
    private val userPreferences: UserPreferences
) : MviViewModel<LoginViewEvent, LoginState, LoginNavigationEffect>(LoginState.Idle) {

    val newEmail: MutableStateFlow<String> = MutableStateFlow("")
    val newPassword: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: LoginViewEvent) {
        when (event) {
            is OnLoginClick -> authenticate(email = event.email, password = event.password)
            is OnEmailChanged -> newEmail.value = event.email
            is OnPasswordChanged -> newPassword.value = event.password
            OnSignUpClick -> setEffect(NavigateToRegister)
            OnResetPasswordClick -> setEffect(NavigateToResetPassword)
        }
    }

    private fun authenticate(email: String, password: String) {
        setState { LoginState.Loading }

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
            val authState = authService.authenticate(email, password)
            when (authState) {
                is AuthState.Success -> {
                    userPreferences.saveUserUid(authService.currentUserId)
                    setEffect(NavigateToMarketOverview)
                }

                is AuthState.Error -> setState { Error("Invalid email or password") }
            }
        }
    }
}
