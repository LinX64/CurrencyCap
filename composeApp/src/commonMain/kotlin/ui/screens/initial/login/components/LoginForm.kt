package ui.screens.initial.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import ui.components.base.EmailTextField
import ui.components.base.PasswordTextField
import ui.components.base.button.PrimaryButton

@Composable
internal fun LoginForm(
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailTextField(
            onEmailChanged = onEmailChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            onPasswordChanged = onPasswordChanged
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Sign In",
            onButtonClick = {
                keyboardController?.hide()
                onLoginClick()
            }
        )
    }
}