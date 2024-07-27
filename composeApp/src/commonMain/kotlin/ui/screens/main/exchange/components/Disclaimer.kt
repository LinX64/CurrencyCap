package ui.screens.main.exchange.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.exchange_screen_disclaimer
import currencycap.composeapp.generated.resources.exchange_screen_disclaimer_description
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun Disclaimer() {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.exchange_screen_disclaimer),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        Text(
            text = stringResource(Res.string.exchange_screen_disclaimer_description),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}
