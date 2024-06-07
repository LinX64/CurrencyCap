package ui.screens.main.components

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

@Composable
internal fun PerformanceChart(
    modifier: Modifier = Modifier,
    list: List<Float>
) {
    val zipList: List<Pair<Float, Float>> = list.zipWithNext()

    Canvas(
        modifier = modifier.fillMaxWidth()
            .height(250.dp)
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
                strokeWidth = 3f
            )
        }

        // Draw horizontal lines with shadow below the chart
        lastToPoint?.let {
            val horizontalLineColor = Color.Gray.copy(alpha = 0.2f)

            val yPosition1 = it.y + (size.height - it.y) * 1 / 4

            drawLine(
                color = horizontalLineColor,
                start = Offset(x = 0f, y = yPosition1),
                end = Offset(x = size.width, y = yPosition1),
                strokeWidth = 1f
            )
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float =
    (value - min) / (max - min)

