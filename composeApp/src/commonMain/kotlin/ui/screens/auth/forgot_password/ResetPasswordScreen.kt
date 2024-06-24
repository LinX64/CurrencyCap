package ui.screens.auth.forgot_password

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.EmailTextField
import ui.screens.auth.forgot_password.ResetPasswordViewEvent.OnEmailChanged
import ui.screens.auth.forgot_password.ResetPasswordViewEvent.OnResetPasswordClick

@Composable
internal fun ResetPasswordScreen(
    padding: PaddingValues,
    resetPasswordViewModel: ResetPasswordViewModel = koinViewModel<ResetPasswordViewModel>(),
    onError: (message: String) -> Unit
) {
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
}

@Composable
private fun ResetPasswordContent(
    modifier: Modifier = Modifier,
    onEmailChanged: (String) -> Unit,
    onResetPasswordClick: () -> Unit
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
                text = "Forgot Password",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailTextField(onEmailChanged = onEmailChanged)
                Spacer(modifier = modifier.height(32.dp))

                Button(
                    onClick = onResetPasswordClick,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "Send Reset Link",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

