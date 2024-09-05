package ui.screens.initial.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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
import util.isEmailValid

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
            is OnEmailChanged -> newEmail.value = event.email.trim()
            is OnPasswordChanged -> newPassword.value = event.password.trim()
        }
    }

    private fun authenticate(email: String, password: String) {
        when {
            email.isEmpty() -> setEffect(ShowError("Email must not be empty"))
            password.isEmpty() -> setEffect(ShowError("Password must not be empty"))
            email.isEmailValid().not() -> setEffect(ShowError("Invalid email format"))
            else -> authenticateUser(email, password)
        }
    }

    private fun authenticateUser(email: String, password: String) {
        setState { Loading }

        viewModelScope.launch {
            delay(800L)

            val result = authServiceRepository.authenticate(email, password)
            result.fold(
                onSuccess = {
                    userPreferences.saveUserUid(authServiceRepository.currentUserId)
                    setEffect(NavigateToMarketOverview)
                },
                onFailure = { setEffect(ShowError("Error while authenticating: " + it.message)) }
            )
            setState { Idle }
        }
    }
}