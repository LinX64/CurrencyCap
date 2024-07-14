package ui.screens.initial.reset_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import ui.components.base.BaseCenterColumn
import ui.components.base.EmailTextField
import ui.components.base.button.PrimaryButton
import ui.screens.initial.reset_password.ResetPasswordState.Error
import ui.screens.initial.reset_password.ResetPasswordViewEvent.OnEmailChanged
import ui.screens.initial.reset_password.ResetPasswordViewEvent.OnResetPasswordClick
import ui.screens.initial.reset_password.components.PasswordResetDialog

@Composable
internal fun ResetPasswordScreen(
    resetPasswordViewModel: ResetPasswordViewModel = koinViewModel<ResetPasswordViewModel>(),
    padding: PaddingValues,
    onNavigateToLogin: () -> Unit,
    onMessage: (message: String) -> Unit
) {
    val state by resetPasswordViewModel.viewState.collectAsStateWithLifecycle()
    val shouldShowDialog = remember { mutableStateOf(false) }
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        ResetPasswordContent(
            onEmailChanged = { email -> resetPasswordViewModel.handleEvent(OnEmailChanged(email)) },
            onResetPasswordClick = { resetPasswordViewModel.handleEvent(OnResetPasswordClick) }
        )
    }

    when (state) {
        is Error -> onMessage((state as Error).message)
        ResetPasswordState.Success -> shouldShowDialog.value = true
        else -> Unit
    }

    if (shouldShowDialog.value) {
        PasswordResetDialog { onNavigateToLogin() }
    }
}

@Composable
private fun ResetPasswordContent(
    modifier: Modifier = Modifier,
    onEmailChanged: (String) -> Unit,
    onResetPasswordClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Reset Password",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(onEmailChanged = onEmailChanged)

            Spacer(modifier = modifier.height(32.dp))

            PrimaryButton(
                text = "Reset Password",
                onButtonClick = {
                    keyboardController?.hide()
                    onResetPasswordClick()
                }
            )
        }
    }
}

