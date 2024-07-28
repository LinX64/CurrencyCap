package ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.ChartDataPoint
import kotlinx.coroutines.delay
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ui.common.formatToPrice
import kotlin.math.roundToInt

@Composable
internal fun InteractiveCryptoChart(
    modifier: Modifier = Modifier,
    lineColor: Color,
    list: List<ChartDataPoint>,
    lighterColor: Color = MaterialTheme.colorScheme.surface,
    lightLineColor: Color = MaterialTheme.colorScheme.primary
) {
    val highestIndex = remember(list) { list.indices.maxByOrNull { list[it].price } ?: 0 }
    var selectedIndex by remember(highestIndex) { mutableIntStateOf(highestIndex) }
    var isInteracting by remember { mutableStateOf(false) }

    LaunchedEffect(isInteracting) {
        if (!isInteracting) {
            delay(100)
            selectedIndex = highestIndex
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isInteracting = true },
                        onDragEnd = { isInteracting = false },
                        onDragCancel = { isInteracting = false },
                        onDrag = { change, _ ->
                            val x = change.position.x
                            selectedIndex = ((x / size.width) * (list.size - 1))
                                .roundToInt()
                                .coerceIn(0, list.size - 1)
                        }
                    )
                }
        ) {
            val prices = list.map { it.price.toFloat() }
            val max = prices.maxOrNull() ?: 0f
            val min = prices.minOrNull() ?: 0f
            val dotColor = Color.White

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

            // Create a path for the shadow
            val shadowPath = Path().apply {
                addPath(path)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            // Draw the shadow
            drawPath(
                path = shadowPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        lineColor.copy(alpha = 0.1f),
                        lineColor.copy(alpha = 0.001f)
                    ),
                    startY = 0f,
                    endY = size.height * 0.9f
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

            // Draw dot at the highest point only when not interacting
            if (list.isNotEmpty() && !isInteracting) {
                val highestValuePercentage =
                    getValuePercentageForRange(list[highestIndex].price.toFloat(), max, min)
                val x = highestIndex * sizeWidthPerPair
                val y = size.height * (1 - highestValuePercentage)
                drawCircle(
                    color = dotColor,
                    radius = 14f,
                    center = Offset(x, y)
                )
            }

            // Draw selected point
            selectedIndex.let { index ->
                val valuePercentage = getValuePercentageForRange(list[index].price.toFloat(), max, min)
                val x = index * sizeWidthPerPair
                val y = size.height * (1 - valuePercentage)
                drawCircle(
                    color = dotColor,
                    radius = 14f,
                    center = Offset(x, y)
                )
            }
        }

        // Overlay for selected data point information, only shown when interacting
        if (isInteracting) {
            selectedIndex.let { index ->
                val dataPoint = list[index]
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Price: $${formatToPrice(dataPoint.price.toDouble())}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Time: ${formatTimestamp(dataPoint.timestamp)}",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    return if (max - min == 0f) 0f else (value - min) / (max - min)
}

private fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.monthNumber}/${dateTime.dayOfMonth}, ${dateTime.hour}:${dateTime.minute}"
}