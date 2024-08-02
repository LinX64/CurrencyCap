package ui.screens.main.overview.components

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
import kotlinx.collections.immutable.ImmutableList
import ui.theme.colors.CurrencyColors

@Composable
fun TopMoversChart(
    modifier: Modifier = Modifier,
    shadowColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
    list: ImmutableList<Float>,
    lighterColor: Color = CurrencyColors.Green.primary,
    lightLineColor: Color = CurrencyColors.Green.light
) {
    val shadowColorStart = if (list.isNotEmpty()) lighterColor.copy(alpha = 0.3f) else shadowColor
    val shadowColorEnd = if (list.isNotEmpty()) Color.Black.copy(alpha = 0.8f) else shadowColor.copy(alpha = 0.1f)

    Canvas(modifier = modifier) {
        val max = list.maxOrNull() ?: 0f
        val min = list.minOrNull() ?: 0f
        val highestIndex = list.indexOf(max)

        val sizeWidthPerPair = if (list.size > 1) size.width / (list.size - 1) else size.width

        // Create a path for the line
        val path = Path().apply {
            if (list.isNotEmpty()) {
                val startValuePercentage = getValuePercentageForRange(list[0], max, min)
                moveTo(0f, size.height * (1 - startValuePercentage))
                list.forEachIndexed { index, value ->
                    val valuePercentage = getValuePercentageForRange(value, max, min)
                    lineTo(index * sizeWidthPerPair, size.height * (1 - valuePercentage))
                }
            }
        }

        // Create a path for the shadow, extending below the main path
        val shadowPath = Path().apply {
            if (list.isNotEmpty()) {
                val startValuePercentage = getValuePercentageForRange(list[0], max, min)
                moveTo(0f, size.height * (1 - startValuePercentage))
                list.forEachIndexed { index, value ->
                    val valuePercentage = getValuePercentageForRange(value, max, min)
                    val x = index * sizeWidthPerPair
                    val y = size.height * (1 - valuePercentage)
                    lineTo(x, y)
                }
                // Continue to bottom-right corner
                lineTo(size.width, size.height)
                // Move to bottom-left corner and close the path
                lineTo(0f, size.height)
                close()
            }
        }

        // Draw gradient shadow below the line
        drawPath(
            path = shadowPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    shadowColorStart,
                    shadowColorEnd
                ),
                startY = 0f,
                endY = size.height
            )
        )

        // Draw the main line with gradient
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
            val highestValuePercentage = getValuePercentageForRange(list[highestIndex], max, min)
            val x = highestIndex * sizeWidthPerPair
            val y = size.height * (1 - highestValuePercentage)

            // Draw glow effect for the dot
            for (i in 3 downTo 0) {
                drawCircle(
                    color = CurrencyColors.Orange.primary.copy(alpha = 0.2f * (4 - i)),
                    radius = 18f - i * 3,
                    center = Offset(x, y)
                )
            }

            // Draw the main dot
            drawCircle(
                color = CurrencyColors.Orange.primary,
                radius = 8f,
                center = Offset(x, y)
            )
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    return if (max - min == 0f) 0f else (value - min) / (max - min)
}