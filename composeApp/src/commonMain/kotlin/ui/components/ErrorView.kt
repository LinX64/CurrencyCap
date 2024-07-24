package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.error
import currencycap.composeapp.generated.resources.please_check_internet_connection
import currencycap.composeapp.generated.resources.retry
import currencycap.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.stringResource
import ui.components.base.button.SecondaryButton
import ui.theme.AppDimensions.ICON_SIZE_64
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun ErrorView(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.something_went_wrong),
    message: String = stringResource(Res.string.please_check_internet_connection),
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = stringResource(Res.string.error),
            modifier = Modifier.size(ICON_SIZE_64),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondaryButton(
            modifier = Modifier.padding(horizontal = SPACER_PADDING_32),
            text = stringResource(Res.string.retry),
            onButtonClick = onRetry
        )
    }
}