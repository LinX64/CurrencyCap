package ui.screens.initial.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mvicompose.linx64.ui.components.HandleNavigationEffect
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.forgot_password
import currencycap.composeapp.generated.resources.login
import di.koinViewModel
import org.jetbrains.compose.resources.stringResource
import ui.components.base.CenteredColumn
import ui.screens.initial.login.LoginNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.login.LoginNavigationEffect.NavigateToRegister
import ui.screens.initial.login.LoginNavigationEffect.NavigateToResetPassword
import ui.screens.initial.login.LoginState.Error
import ui.screens.initial.login.LoginState.Loading
import ui.screens.initial.login.LoginViewEvent.OnEmailChanged
import ui.screens.initial.login.LoginViewEvent.OnLoginClick
import ui.screens.initial.login.LoginViewEvent.OnPasswordChanged
import ui.screens.initial.login.LoginViewEvent.OnResetPasswordClick
import ui.screens.initial.login.components.LoginForm
import ui.screens.initial.login.components.MadeWithLove
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun LoginRoute(
    loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    navigateToMarketOverview: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToResetPassword: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by loginViewModel.viewState.collectAsStateWithLifecycle()
    val email = loginViewModel.newEmail.value
    val password = loginViewModel.newPassword.value

    LoginScreen(
        email = email,
        password = password,
        handleEvent = loginViewModel::handleEvent
    )

    HandleNavigationEffect(loginViewModel) { effect ->
        when (effect) {
            is NavigateToMarketOverview -> navigateToMarketOverview()
            NavigateToRegister -> navigateToRegister()
            NavigateToResetPassword -> navigateToResetPassword()
        }
    }

    when (state) {
        is Loading -> CenteredColumn { CircularProgressIndicator() }
        is Error -> onError((state as Error).message)
        else -> Unit
    }
}

@Composable
private fun LoginScreen(
    padding: PaddingValues = PaddingValues(SPACER_PADDING_16),
    email: String,
    password: String,
    handleEvent: (LoginViewEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SPACER_PADDING_16),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.login),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(24.dp))

            LoginForm(
                onEmailChanged = { handleEvent(OnEmailChanged(it)) },
                onPasswordChanged = { handleEvent(OnPasswordChanged(it)) },
                onLoginClick = { handleEvent(OnLoginClick(email, password)) }
            )

            Spacer(modifier = Modifier.height(SPACER_PADDING_16))

            TextButton(onClick = { handleEvent(OnResetPasswordClick) }) {
                Text(
                    text = stringResource(Res.string.forgot_password),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = SPACER_PADDING_16),
            contentAlignment = Alignment.BottomCenter
        ) {
            MadeWithLove()
        }
    }
}

