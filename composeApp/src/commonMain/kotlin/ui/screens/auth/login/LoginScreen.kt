package ui.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.client.auth.components.PasswordTextField
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.screens.auth.login.LoginViewEvent.OnEmailChanged
import ui.screens.auth.login.components.EmailTextField

@Composable
internal fun LoginScreen(
    padding: PaddingValues = PaddingValues(16.dp),
    loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    BaseCenterColumn(
        modifier = Modifier.fillMaxSize()
            .padding(padding),
    ) {
        LoginForm(
            onEmailChanged = { loginViewModel.handleEvent(OnEmailChanged(it)) },
            onPasswordChanged = { loginViewModel.handleEvent(OnEmailChanged(it)) }
        )
    }

    Column {
        MadeWithLove()
    }
}

@Composable
private fun LoginForm(
    modifier: Modifier = Modifier,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
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
                text = "Login",
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
                    isEmailValid = { },
                    onEmailChanged = onEmailChanged
                )

                Spacer(modifier = modifier.height(10.dp))

                PasswordTextField(
                    isPasswordValid = { },
                    onPasswordChanged = onPasswordChanged
                )

                Spacer(modifier = modifier.height(32.dp))

                Button(
                    onClick = { },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

@Composable
internal fun MadeWithLove() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Made with ❤️ in Poland 🇵🇱",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}