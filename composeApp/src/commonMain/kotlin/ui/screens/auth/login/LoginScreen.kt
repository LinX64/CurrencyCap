package ui.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseBlurDialog
import ui.components.BaseCenterColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.LoginViewEvent.OnErrorDialogDismissed
import ui.screens.auth.login.LoginViewEvent.OnLoginClick
import ui.screens.auth.login.LoginViewEvent.OnPasswordChanged
import ui.screens.auth.login.components.EmailTextField
import ui.screens.auth.login.components.PasswordTextField

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

@Composable
private fun OnErrorDialog(
    message: String,
    onDismissRequest: () -> Unit
) {
    BaseBlurDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = "Dismiss",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Composable
private fun LoginForm(
    modifier: Modifier = Modifier,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailTextField(
                    onEmailChanged = onEmailChanged
                )

                Spacer(modifier = modifier.height(10.dp))

                PasswordTextField(
                    onPasswordChanged = onPasswordChanged
                )

                Spacer(modifier = modifier.height(32.dp))

                Button(
                    onClick = onLoginClick,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

@Composable
internal fun MadeWithLove() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Made with ❤️ in Poland 🇵🇱",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}