package ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import domain.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.login.LoginNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.login.LoginState.Error
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
            val user = authService.authenticate(email, password).user
            if (user?.uid?.isNotBlank() == true) {
                setEffect(NavigateToMarketOverview(user.uid))
            } else {
                setState { Error("Could not authenticate user!") }
            }
        }
    }
}
