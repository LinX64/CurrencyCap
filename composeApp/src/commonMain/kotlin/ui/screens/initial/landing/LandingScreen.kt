package ui.screens.initial.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import ui.components.PrimaryButton
import ui.components.SecondaryButton

@Composable
internal fun LandingScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(220.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Welcome to CurrencyCap",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Track your crypto investments with ease",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(80.dp))

            PrimaryButton(
                onButtonClick = onSignUpClick,
                text = "Create an Account"
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryButton(
                onButtonClick = onLoginClick,
                text = "Log In"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.clickable { onPrivacyPolicyClick() }
            )
        }
    }
}
