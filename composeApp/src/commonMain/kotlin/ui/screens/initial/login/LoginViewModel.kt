package ui.screens.initial.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.remote.repository.auth.AuthServiceRepositoryImpl.AuthState
import domain.repository.AuthServiceRepository
import domain.repository.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.initial.login.LoginNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.login.LoginNavigationEffect.NavigateToRegister
import ui.screens.initial.login.LoginNavigationEffect.NavigateToResetPassword
import ui.screens.initial.login.LoginNavigationEffect.ShowError
import ui.screens.initial.login.LoginState.Idle
import ui.screens.initial.login.LoginState.Loading
import ui.screens.initial.login.LoginViewEvent.OnEmailChanged
import ui.screens.initial.login.LoginViewEvent.OnLoginClick
import ui.screens.initial.login.LoginViewEvent.OnPasswordChanged
import ui.screens.initial.login.LoginViewEvent.OnResetPasswordClick
import ui.screens.initial.login.LoginViewEvent.OnSignUpClick
import util.validateEmail

internal class LoginViewModel(
    private val authServiceRepository: AuthServiceRepository,
    private val userPreferences: UserPreferences
) : MviViewModel<LoginViewEvent, LoginState, LoginNavigationEffect>(Idle) {

    val newEmail = mutableStateOf("")
    val newPassword = mutableStateOf("")

    override fun handleEvent(event: LoginViewEvent) {
        when (event) {
            OnSignUpClick -> setEffect(NavigateToRegister)
            OnResetPasswordClick -> setEffect(NavigateToResetPassword)
            is OnLoginClick -> authenticate(email = event.email, password = event.password)
            is OnEmailChanged -> newEmail.value = event.email
            is OnPasswordChanged -> newPassword.value = event.password
        }
    }

    private fun authenticate(email: String, password: String) {
        when {
            email.isEmpty() -> setEffect(ShowError("Email must not be empty"))
            password.isEmpty() -> setEffect(ShowError("Password must not be empty"))
            email.validateEmail().not() -> setEffect(ShowError("Invalid email format"))
            else -> authenticateUser(email, password)
        }
    }

    private fun authenticateUser(email: String, password: String) {
        setState { Loading }

        viewModelScope.launch {
            delay(1000)

            when (authServiceRepository.authenticate(email, password)) {
                AuthState.Loading -> Unit
                is AuthState.Success -> {
                    userPreferences.saveUserUid(authServiceRepository.currentUserId)
                    setEffect(NavigateToMarketOverview)
                }
                is AuthState.Error -> setEffect(ShowError("Invalid email or password"))
            }

            setState { Idle }
        }
    }
}