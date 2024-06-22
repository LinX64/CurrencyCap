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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.CenteredColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.login.components.EmailTextField
import ui.screens.auth.register.RegisterNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.register.RegisterViewEvent.OnEmailChanged
import ui.screens.auth.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.auth.register.RegisterViewEvent.OnRegisterClick
import ui.screens.auth.register.components.PasswordTextField

@Composable
internal fun RegisterScreen(
    padding: PaddingValues,
    registerViewModel: RegisterViewModel = koinViewModel<RegisterViewModel>(),
    navigateToMarketOverview: (uid: String) -> Unit,
    onError: (message: String) -> Unit
) {
    val state by registerViewModel.viewState.collectAsState()
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        RegisterForm(
            onEmailChanged = { registerViewModel.handleEvent(OnEmailChanged(it)) },
            onPasswordChanged = { registerViewModel.handleEvent(OnPasswordChanged(it)) },
            onSignUpClick = { registerViewModel.handleEvent(OnRegisterClick) }
        )
    }

    HandleNavigationEffect(registerViewModel) { effect ->
        when (effect) {
            is NavigateToMarketOverview -> navigateToMarketOverview(effect.uid)
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

                Spacer(modifier = modifier.height(32.dp))

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

