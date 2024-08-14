package ui.screens.initial.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.login
import org.jetbrains.compose.resources.stringResource
import ui.components.base.EmailTextField
import ui.components.base.PasswordTextField
import ui.components.base.button.PrimaryButton
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun LoginForm(
    isLoading: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailTextField(
            onEmailChanged = onEmailChanged
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        PasswordTextField(
            onPasswordChanged = onPasswordChanged
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            isLoading = isLoading,
            text = stringResource(Res.string.login),
            onButtonClick = {
                keyboardController?.hide()
                onLoginClick()
            }
        )
    }
}