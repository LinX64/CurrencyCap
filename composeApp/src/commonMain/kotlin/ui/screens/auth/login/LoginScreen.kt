package ui.screens.auth.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import di.koinViewModel
import ui.components.CenteredColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.login.LoginNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.login.LoginNavigationEffect.NavigateToRegister
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.LoginViewEvent.OnLoginClick
import ui.screens.auth.login.LoginViewEvent.OnPasswordChanged
import ui.screens.auth.login.LoginViewEvent.OnResetPasswordClick
import ui.screens.auth.login.components.LoginForm
import ui.screens.auth.login.components.MadeWithLove

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    padding: PaddingValues,
    navigateToMarketOverview: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToResetPassword: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by loginViewModel.viewState.collectAsState()

    Content(
        loginViewModel = loginViewModel,
        padding = padding
    )

    HandleNavigationEffect(loginViewModel) { effect ->
        when (effect) {
            is NavigateToMarketOverview -> navigateToMarketOverview()
            NavigateToRegister -> navigateToRegister()
            LoginNavigationEffect.NavigateToResetPassword -> navigateToResetPassword()
        }
    }

    when (state) {
        is LoginState.Loading -> CenteredColumn { CircularProgressIndicator() }
        is LoginState.Error -> onError((state as LoginState.Error).message)
        else -> Unit
    }
}

@Composable
private fun Content(
    loginViewModel: LoginViewModel,
    padding: PaddingValues
) {
    val email by loginViewModel.newEmail.collectAsState()
    val password by loginViewModel.newPassword.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(padding)
    ) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-20).dp)
        ) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.2f),
                radius = size.minDimension / 2
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))

            LoginForm(
                onEmailChanged = { loginViewModel.handleEvent(OnEmailChanged(it)) },
                onPasswordChanged = { loginViewModel.handleEvent(OnPasswordChanged(it)) },
                onLoginClick = { loginViewModel.handleEvent(OnLoginClick(email, password)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { loginViewModel.handleEvent(OnResetPasswordClick) }) {
                Text(
                    text = "Forgot your password?",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            }
        }

        Canvas(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-80).dp, y = 20.dp)
        ) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.2f),
                radius = size.minDimension / 2
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            MadeWithLove()
        }
    }
}

