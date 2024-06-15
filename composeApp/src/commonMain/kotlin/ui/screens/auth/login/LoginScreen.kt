package ui.screens.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.LoginViewEvent.OnErrorDialogDismissed
import ui.screens.auth.login.LoginViewEvent.OnLoginClick
import ui.screens.auth.login.LoginViewEvent.OnPasswordChanged
import ui.screens.auth.login.components.LoginForm
import ui.screens.auth.login.components.MadeWithLove
import ui.screens.auth.login.components.OnErrorDialog

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    padding: PaddingValues = PaddingValues(16.dp),
    onLoginSuccess: () -> Unit
) {
    val state by loginViewModel.viewState.collectAsState()
    val email by loginViewModel.newEmail.collectAsState()
    val password by loginViewModel.newPassword.collectAsState()

    BaseCenterColumn(
        modifier = Modifier.fillMaxSize().padding(padding)
    ) {
        LoginForm(
            onEmailChanged = { loginViewModel.handleEvent(OnEmailChanged(it)) },
            onPasswordChanged = { loginViewModel.handleEvent(OnPasswordChanged(it)) },
            onLoginClick = { loginViewModel.handleEvent(OnLoginClick(email, password)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Error -> OnErrorDialog(
                message = (state as LoginState.Error).message,
                onDismissRequest = { loginViewModel.handleEvent(OnErrorDialogDismissed) }
            )

            else -> Unit
        }
    }

    Column {
        MadeWithLove()
    }

    HandleNavigationEffect(loginViewModel) { effect ->
        when (effect) {
            is LoginNavigationEffect.LoginSuccess -> onLoginSuccess()
        }
    }
}
