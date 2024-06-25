package ui.screens.auth.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.EmailTextField

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

        Button(
            onClick = {
                keyboardController?.hide()
                onLoginClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black
            )
        ) {
            Text(text = "Sign In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}


//@Composable
//private fun LoginForm(
//    modifier: Modifier = Modifier,
//    onEmailChanged: (String) -> Unit,
//    onPasswordChanged: (String) -> Unit,
//    onLoginClick: () -> Unit
//) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    Column(
//        modifier = Modifier
//            .wrapContentHeight()
//            .padding(horizontal = 16.dp, vertical = 16.dp)
//            .background(
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
//                shape = RoundedCornerShape(20.dp)
//            )
//    ) {
//        Column(
//            modifier = Modifier.padding(25.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "Login",
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onSurface,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                EmailTextField(
//                    onEmailChanged = onEmailChanged
//                )
//
//                Spacer(modifier = modifier.height(10.dp))
//
//                PasswordTextField(
//                    onPasswordChanged = onPasswordChanged
//                )
//
//                Spacer(modifier = modifier.height(32.dp))
//
//                Button(
//                    onClick = {
//                        keyboardController?.hide()
//                        onLoginClick()
//                    },
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .height(52.dp),
//                    shape = RoundedCornerShape(10.dp),
//                ) {
//                    Text(
//                        text = "Sign In",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.surface
//                    )
//                }
//            }
//        }
//    }
//}