package ui.components.base.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors

@Composable
internal fun SecondarySmallIconButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Outlined.Done,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onButtonClick: () -> Unit,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            CurrencyColors.Purple.primary,
            CurrencyColors.Purple.light
        )
    )

    Card(
        modifier = Modifier
            .background(
                brush = gradient,
                shape = RoundedCornerShape(CARD_CORNER_RADIUS)
            )
            .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
            .clickable(onClick = onButtonClick)
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = SPACER_PADDING_16, vertical = SPACER_PADDING_8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_8)
        ) {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(Res.string.icon)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}