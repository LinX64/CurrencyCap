package ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun HorizontalLineWithDot() {
    val dark = isSystemInDarkTheme()
    val isDarkTheme = if (dark) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = SPACER_PADDING_8)
    ) {
        Canvas(
            modifier = Modifier.fillMaxWidth()
                .height(20.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height / 2

            val path = Path().apply {
                moveTo(0f, canvasHeight)
                lineTo(canvasWidth, canvasHeight)
            }

            drawPath(
                path = path,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Gray.copy(alpha = 0f),
                        Color.Gray.copy(alpha = 1f),
                        Color.Gray.copy(alpha = 1f),
                        Color.Gray.copy(alpha = 0f)
                    ),
                    startX = 0f,
                    endX = canvasWidth
                ),
                style = Stroke(width = 3f)
            )

            drawCircle(
                color = isDarkTheme,
                radius = 8f,
                center = Offset(canvasWidth / 2, canvasHeight)
            )
        }
    }
}