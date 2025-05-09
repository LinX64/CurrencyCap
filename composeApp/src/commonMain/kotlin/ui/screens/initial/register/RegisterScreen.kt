package ui.screens.initial.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.and
import currencycap.composeapp.generated.resources.by_signing_up
import currencycap.composeapp.generated.resources.privacy_policy
import currencycap.composeapp.generated.resources.register
import currencycap.composeapp.generated.resources.sign_up
import currencycap.composeapp.generated.resources.terms_of_service
import di.koinViewModel
import org.jetbrains.compose.resources.stringResource
import ui.components.base.EmailTextField
import ui.components.base.HandleNavigationEffect
import ui.components.base.PasswordTextField
import ui.components.base.button.PrimaryButton
import ui.screens.initial.register.RegisterNavigationEffect.NavigateToFillProfile
import ui.screens.initial.register.RegisterNavigationEffect.ShowError
import ui.screens.initial.register.RegisterViewEvent.OnEmailChanged
import ui.screens.initial.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.initial.register.RegisterViewEvent.OnRegisterClick
import ui.screens.initial.register.components.LogInText
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_24
import ui.theme.AppDimensions.SPACER_PADDING_32

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
        isLoading = state is RegisterState.Loading,
        onEmailChanged = { registerViewModel.handleEvent(OnEmailChanged(it)) },
        onPasswordChanged = { registerViewModel.handleEvent(OnPasswordChanged(it)) },
        onSignUpClick = { registerViewModel.handleEvent(OnRegisterClick) },
        navigateToLogin = navigateToLogin,
        onTermsOfServiceClick = onTermsOfServiceClick
    )

    HandleNavigationEffect(registerViewModel) { effect ->
        when (effect) {
            is NavigateToFillProfile -> onNavigateToFillProfile()
            is ShowError -> onError(effect.message)
        }
    }
}

@Composable
private fun RegisterContent(
    isLoading: Boolean = false,
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
            .padding(horizontal = SPACER_PADDING_32),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.register),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(SPACER_PADDING_24))

        EmailTextField(
            onEmailChanged = onEmailChanged
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        PasswordTextField(
            onPasswordChanged = onPasswordChanged
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        BySigningUpText(onTermsOfServiceClick = onTermsOfServiceClick)

        Spacer(modifier = Modifier.height(SPACER_PADDING_24))

        PrimaryButton(
            isLoading = isLoading,
            text = stringResource(Res.string.sign_up),
            onButtonClick = {
                keyboardController?.hide()
                onSignUpClick()
            }
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        LogInText(onLogInClick = navigateToLogin)
    }
}

@Composable
private fun BySigningUpText(
    onTermsOfServiceClick: () -> Unit
) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(Res.string.by_signing_up))
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                pushStringAnnotation("TOS", "")
                append(stringResource(Res.string.terms_of_service))
                pop()
            }
            append(stringResource(Res.string.and))
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                pushStringAnnotation("PP", "")
                append(stringResource(Res.string.privacy_policy))
                pop()
            }
        },
        fontSize = 12.sp,
        color = Color.LightGray,
        modifier = Modifier.clickable { onTermsOfServiceClick() }
    )
}