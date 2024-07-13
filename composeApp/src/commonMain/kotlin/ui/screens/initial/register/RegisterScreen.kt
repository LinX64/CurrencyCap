package ui.screens.initial.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import ui.components.CenteredColumn
import ui.components.EmailTextField
import ui.components.HandleNavigationEffect
import ui.components.PasswordTextField
import ui.components.PrimaryButton
import ui.screens.initial.register.RegisterNavigationEffect.NavigateToFillProfile
import ui.screens.initial.register.RegisterViewEvent.OnEmailChanged
import ui.screens.initial.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.initial.register.RegisterViewEvent.OnRegisterClick
import ui.screens.initial.register.components.LogInText

@Composable
internal fun RegisterScreen(
    registerViewModel: RegisterViewModel = koinViewModel<RegisterViewModel>(),
    onNavigateToFillProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    onError: (message: String) -> Unit,
    onTermsOfServiceClick: () -> Unit
) {
    val state by registerViewModel.viewState.collectAsStateWithLifecycle()
    RegisterContent(
        onEmailChanged = { registerViewModel.handleEvent(OnEmailChanged(it)) },
        onPasswordChanged = { registerViewModel.handleEvent(OnPasswordChanged(it)) },
        onSignUpClick = { registerViewModel.handleEvent(OnRegisterClick) },
        navigateToLogin = navigateToLogin,
        onTermsOfServiceClick = onTermsOfServiceClick
    )

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
private fun RegisterContent(
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignUpClick: () -> Unit,
    navigateToLogin: () -> Unit,
    onTermsOfServiceClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))

        EmailTextField(
            onEmailChanged = onEmailChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            onPasswordChanged = onPasswordChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        BySigningUpText(onTermsOfServiceClick = onTermsOfServiceClick)

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Sign Up",
            onButtonClick = {
                keyboardController?.hide()
                onSignUpClick()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LogInText(onLogInClick = navigateToLogin)
    }
}

@Composable
private fun BySigningUpText(
    onTermsOfServiceClick: () -> Unit
) {
    Text(
        text = buildAnnotatedString {
            append("By signing up, you agree to our ")
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                pushStringAnnotation("TOS", "")
                append("Terms of Service")
                pop()
            }
            append(" and ")
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                pushStringAnnotation("PP", "")
                append("Privacy Policy")
                pop()
            }
        },
        fontSize = 12.sp,
        color = Color.LightGray,
        modifier = Modifier.clickable { onTermsOfServiceClick() }
    )
}