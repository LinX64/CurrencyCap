package ui.screens.initial.login

import androidx.lifecycle.viewModelScope
import data.remote.repository.auth.AuthServiceRepositoryImpl.AuthState
import domain.repository.AuthServiceRepository
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.initial.login.LoginNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.login.LoginNavigationEffect.NavigateToRegister
import ui.screens.initial.login.LoginNavigationEffect.NavigateToResetPassword
import ui.screens.initial.login.LoginState.Error
import ui.screens.initial.login.LoginViewEvent.OnEmailChanged
import ui.screens.initial.login.LoginViewEvent.OnLoginClick
import ui.screens.initial.login.LoginViewEvent.OnPasswordChanged
import ui.screens.initial.login.LoginViewEvent.OnResetPasswordClick
import ui.screens.initial.login.LoginViewEvent.OnSignUpClick
import util.validateEmail

internal class LoginViewModel(
    private val authServiceRepository: AuthServiceRepository,
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
            val authState = authServiceRepository.authenticate(email, password)
            when (authState) {
                AuthState.Loading -> setState { LoginState.Loading }
                is AuthState.Success -> {
                    userPreferences.saveUserUid(authServiceRepository.currentUserId)
                    setEffect(NavigateToMarketOverview)
                }

                is AuthState.Error -> setState { Error("Invalid email or password") }
            }
        }
    }
}
