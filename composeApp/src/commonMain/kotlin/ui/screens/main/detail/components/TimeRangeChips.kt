package ui.screens.main.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import ui.theme.AppDimensions.CARD_CORNER_RADIUS

@Composable
internal fun TimeRangeChips(
    timeRanges: ImmutableSet<String> = persistentSetOf("24H", "1W", "1M", "1Y", "All"),
    selectedRange: String,
    onRangeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        timeRanges.forEach { range ->
            AssistChip(
                onClick = { onRangeSelected(range) },
                label = {
                    Text(
                        text = range,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                shape = RoundedCornerShape(CARD_CORNER_RADIUS),
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (range == selectedRange)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surface,
                    labelColor = if (range == selectedRange)
                        MaterialTheme.colorScheme.surface
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}
