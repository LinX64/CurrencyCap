package ui.screens.main.overview.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun PerformanceChart(
    modifier: Modifier = Modifier,
    list: ImmutableList<Float>
) {
    val zipList: List<Pair<Float, Float>> = list.zipWithNext()
    Canvas(
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
            .then(modifier)
    ) {
        val max = list.maxOrNull() ?: 0f
        val min = list.minOrNull() ?: 0f

        val lineColor = if (list.last() > list.first()) Color.Green else Color.Red
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(
                lineColor.copy(alpha = 0.3f),
                lineColor.copy(alpha = 0.0f)
            )
        )

        val sizeWidthPerPair = size.width / zipList.size
        var lastToPoint: Offset? = null

        for ((index, pair) in zipList.withIndex()) {
            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

            val fromPoint =
                Offset(x = index * sizeWidthPerPair, y = size.height.times(1 - fromValuePercentage))
            val toPoint = Offset(
                x = (index + 1) * sizeWidthPerPair,
                y = size.height.times(1 - toValuePercentage)
            )
            lastToPoint = toPoint

            // Draw gradient shadow below the line
            val path = Path().apply {
                moveTo(fromPoint.x, fromPoint.y)
                lineTo(toPoint.x, toPoint.y)
                lineTo(toPoint.x, size.height)
                lineTo(fromPoint.x, size.height)
                close()
            }
            drawPath(
                path = path,
                brush = gradientBrush
            )

            // Draw the line
            drawLine(
                color = lineColor,
                start = fromPoint,
                end = toPoint,
                strokeWidth = 5f
            )
        }
        lastToPoint?.let {
            val horizontalLineColor = Color.Gray.copy(alpha = 0.3f)
            val yPositionBottom = size.height.times(1 - getValuePercentageForRange(min, max, min))
            val yPositionAboveBottom = yPositionBottom - 20.dp.toPx()

            drawLine(
                color = horizontalLineColor,
                start = Offset(x = 0f, y = yPositionBottom),
                end = Offset(x = size.width, y = yPositionBottom),
                strokeWidth = 2f
            )

            drawLine(
                color = horizontalLineColor,
                start = Offset(x = 0f, y = yPositionAboveBottom),
                end = Offset(x = size.width, y = yPositionAboveBottom),
                strokeWidth = 2f
            )
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float =
    if (max == min) 0f else (value - min) / (max - min)


