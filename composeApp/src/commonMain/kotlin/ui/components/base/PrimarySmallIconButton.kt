package ui.components.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import ui.theme.colors.CurrencyColors

@Composable
internal fun PrimarySmallIconButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Outlined.Done,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onButtonClick: () -> Unit,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            CurrencyColors.Muted_Teal.primary,
            CurrencyColors.Green.primary
        )
    )

    IconButton(
        modifier = Modifier
            .background(
                brush = gradient,
                shape = RoundedCornerShape(35.dp)
            )
            .clip(RoundedCornerShape(35.dp))
            .then(modifier),
        onClick = onButtonClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
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