package ui.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import di.koinViewModel
import ui.components.CenteredColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.register.RegisterNavigationEffect.NavigateToFillProfile
import ui.screens.auth.register.RegisterViewEvent.OnEmailChanged
import ui.screens.auth.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.auth.register.RegisterViewEvent.OnRegisterClick
import ui.screens.auth.register.components.LogInText

@Composable
internal fun RegisterScreen(
    registerViewModel: RegisterViewModel = koinViewModel<RegisterViewModel>(),
    onNavigateToFillProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by registerViewModel.viewState.collectAsState()
    RegisterContent(
        onEmailChanged = { registerViewModel.handleEvent(OnEmailChanged(it)) },
        onPasswordChanged = { registerViewModel.handleEvent(OnPasswordChanged(it)) },
        onSignUpClick = { registerViewModel.handleEvent(OnRegisterClick) },
        navigateToLogin = navigateToLogin
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
    navigateToLogin: () -> Unit
) {
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

        OutlinedTextField(
            value = "",
            onValueChange = onEmailChanged,
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF03DAC5),
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = onPasswordChanged,
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF03DAC5),
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "By signing up, you agree to our Terms of Service and Privacy Policy",
            fontSize = 12.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black
            )
        ) {
            Text(text = "Sign Up", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LogInText(onLogInClick = navigateToLogin)
    }
}
