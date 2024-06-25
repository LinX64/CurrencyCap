package ui.screens.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.CenteredColumn
import ui.components.EmailTextField
import ui.components.HandleNavigationEffect
import ui.screens.auth.register.RegisterNavigationEffect.NavigateToFillProfile
import ui.screens.auth.register.RegisterViewEvent.OnEmailChanged
import ui.screens.auth.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.auth.register.RegisterViewEvent.OnRegisterClick
import ui.screens.auth.register.components.PasswordTextField

@Composable
internal fun RegisterScreen(
    padding: PaddingValues,
    registerViewModel: RegisterViewModel = koinViewModel<RegisterViewModel>(),
    onNavigateToFillProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by registerViewModel.viewState.collectAsState()
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        RegisterForm(
            onEmailChanged = { registerViewModel.handleEvent(OnEmailChanged(it)) },
            onPasswordChanged = { registerViewModel.handleEvent(OnPasswordChanged(it)) },
            onSignUpClick = { registerViewModel.handleEvent(OnRegisterClick) }
        )

        LogInText(onLogInClick = navigateToLogin)
    }

    HandleNavigationEffect(registerViewModel) { effect ->
        when (effect) {
            is NavigateToFillProfile -> onNavigateToFillProfile()
        }
    }

    when (state) {
        is RegisterState.Loading -> CenteredColumn { CircularProgressIndicator() }
        is RegisterState.Error -> onError((state as RegisterState.Error).message)
        else -> Unit
    }
}

@Composable
private fun RegisterForm(
    modifier: Modifier = Modifier,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 16.dp)
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
                text = "Sign up with Email",
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

                Spacer(modifier = modifier.height(16.dp))

                Text(
                    text = "By signing up, you agree to our Terms of Service and Privacy Policy",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = modifier.height(16.dp))

                Button(
                    onClick = { onSignUpClick() },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

@Composable
private fun LogInText(
    onLogInClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append("Already have an account? ")
        pushStringAnnotation(tag = "LogIn", annotation = "LogIn")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            append("Log in")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        modifier = Modifier.padding(top = 16.dp),
        onClick = { offset ->
            val position = offset
            annotatedString.getStringAnnotations(tag = "LogIn", start = position, end = position)
                .firstOrNull()?.let {
                    onLogInClick()
                }
        }
    )
}