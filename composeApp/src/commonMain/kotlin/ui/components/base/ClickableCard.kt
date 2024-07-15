package ui.components.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun ClickableCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCardSelected: Boolean = false,
    content: @Composable () -> Unit
) {
    val cardColor = if (isCardSelected) {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
    } else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SPACER_PADDING_8, horizontal = CARD_CORNER_RADIUS)
            .then(Modifier),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        onClick = onClick
    ) {
        content()
    }
}