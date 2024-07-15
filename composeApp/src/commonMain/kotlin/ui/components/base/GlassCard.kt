package ui.components.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.AppDimensions.CARD_CORNER_RADIUS

@Composable
internal fun GlassCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    containerColor: Color = Color.Transparent,
    isClickable: Boolean = false,
    onCardClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.wrapContentSize()
            .then(modifier),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.1f)),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = Color.Transparent
        ),
        onClick = onCardClick,
        enabled = isClickable
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .wrapContentSize()
        ) {
            content()
        }
    }
}