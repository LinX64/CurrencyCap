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

@Composable
fun TopMoversChart(
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.onSurface,
    shadowColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    list: ImmutableList<Float>
) {
    val dotColor = MaterialTheme.colorScheme.onSurface

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
                    shadowColor,
                    shadowColor.copy(alpha = 0.001f)
                ),
                startY = 0f,
                endY = size.height
            )
        )

        // Draw the line
        drawPath(
            path = path,
            color = lineColor,
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