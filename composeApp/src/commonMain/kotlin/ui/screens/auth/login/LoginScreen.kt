package ui.screens.auth.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.CenteredColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.login.LoginNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.login.LoginNavigationEffect.NavigateToRegister
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.LoginViewEvent.OnLoginClick
import ui.screens.auth.login.LoginViewEvent.OnPasswordChanged
import ui.screens.auth.login.LoginViewEvent.OnSignUpClick
import ui.screens.auth.login.components.LoginForm
import ui.screens.auth.login.components.MadeWithLove

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    padding: PaddingValues,
    navigateToMarketOverview: () -> Unit,
    navigateToRegister: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by loginViewModel.viewState.collectAsState()
    val email by loginViewModel.newEmail.collectAsState()
    val password by loginViewModel.newPassword.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
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

        BaseCenterColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            LoginForm(
                onEmailChanged = { loginViewModel.handleEvent(OnEmailChanged(it)) },
                onPasswordChanged = { loginViewModel.handleEvent(OnPasswordChanged(it)) },
                onLoginClick = { loginViewModel.handleEvent(OnLoginClick(email, password)) }
            )

            SignUpText(onSignUpClick = { loginViewModel.handleEvent(OnSignUpClick) })
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

        Column {
            MadeWithLove()
        }
    }

    HandleNavigationEffect(loginViewModel) { effect ->
        when (effect) {
            is NavigateToMarketOverview -> navigateToMarketOverview()
            NavigateToRegister -> navigateToRegister()
        }
    }

    when (state) {
        is LoginState.Loading -> CenteredColumn { CircularProgressIndicator() }
        is LoginState.Error -> onError((state as LoginState.Error).message)
        else -> Unit
    }
}

@Composable
private fun SignUpText(
    onSignUpClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append("Don't have an account? ")
        pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            append("Sign up")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        modifier = Modifier.padding(top = 16.dp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                .firstOrNull()?.let {
                    onSignUpClick()
                }
        }
    )
}
