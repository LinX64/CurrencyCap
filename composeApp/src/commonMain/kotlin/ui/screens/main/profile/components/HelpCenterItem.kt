package ui.screens.main.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.chevron_right
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.colors.CurrencyColors

@Composable
internal fun HelpCenterItem(
    text: String,
    onButtonClick: () -> Unit
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            CurrencyColors.Grey.primary,
            CurrencyColors.Grey.light
        )
    )

    Button(
        modifier = Modifier.fillMaxWidth()
            .padding(start = SPACER_PADDING_16)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(SPACER_PADDING_16)
            ),
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(SPACER_PADDING_16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(Res.string.chevron_right),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
