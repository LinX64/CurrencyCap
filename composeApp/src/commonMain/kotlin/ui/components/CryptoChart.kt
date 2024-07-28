package ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import domain.model.ChartDataPoint
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CryptoChart(
    modifier: Modifier = Modifier,
    lightLineColor: Color = MaterialTheme.colorScheme.onSurface,
    lighterColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    list: ImmutableList<ChartDataPoint>
) {
    val dotColor = MaterialTheme.colorScheme.onSurface

    Canvas(
        modifier = modifier
    ) {
        val prices = list.map { it.price }.map { it.toFloat() }
        val max = prices.maxOrNull() ?: 0f
        val min = prices.minOrNull() ?: 0f
        val highestIndex = prices.indexOf(max)

        val sizeWidthPerPair = if (list.size > 1) size.width / (list.size - 1) else size.width

        // Create a path for the line
        val path = Path().apply {
            if (list.isNotEmpty()) {
                val startValuePercentage = getValuePercentageForRange(list[0].price.toFloat(), max, min)
                moveTo(0f, size.height * (1 - startValuePercentage))
                list.forEachIndexed { index, point ->
                    val valuePercentage = getValuePercentageForRange(point.price.toFloat(), max, min)
                    lineTo(index * sizeWidthPerPair, size.height * (1 - valuePercentage))
                }
            }
        }

        // Create a path for the shadow, extending slightly above and below the main path
        val shadowPath = Path().apply {
            if (list.isNotEmpty()) {
                val startValuePercentage = getValuePercentageForRange(list[0].price.toFloat(), max, min)
                moveTo(0f, size.height * (1 - startValuePercentage) - 10f)
                list.forEachIndexed { index, point ->
                    val valuePercentage = getValuePercentageForRange(point.price.toFloat(), max, min)
                    lineTo(index * sizeWidthPerPair, size.height * (1 - valuePercentage) - 10f)
                }
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
        }

        // Draw gradient shadow around the line
        drawPath(
            path = shadowPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    lightLineColor.copy(alpha = 0.3f),
                    lightLineColor.copy(alpha = 0.1f)
                )
            )
        )

        // Draw the line with gradient brush and rounded corners
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colors = listOf(lighterColor, lightLineColor, lighterColor),
                startX = 0f,
                endX = size.width
            ),
            style = Stroke(
                width = 8f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Draw dot at the highest point
        if (list.isNotEmpty()) {
            val highestValuePercentage = getValuePercentageForRange(list[highestIndex].price.toFloat(), max, min)
            val x = highestIndex * sizeWidthPerPair
            val y = size.height * (1 - highestValuePercentage)
            drawCircle(
                color = dotColor,
                radius = 14f,
                center = Offset(x, y)
            )
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    return if (max - min == 0f) 0f else (value - min) / (max - min)
}