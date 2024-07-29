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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    list: List<ChartDataPoint>,
    isInteractivityEnabled: Boolean = true,
    isLoading: Boolean = false,
    lighterColor: Color = MaterialTheme.colorScheme.surface,
    lightLineColor: Color = MaterialTheme.colorScheme.primary,
) {
    val highestIndex = remember(list) { list.indices.maxByOrNull { list[it].price } ?: 0 }
    var selectedIndex by remember(highestIndex) { mutableIntStateOf(highestIndex) }
    var isInteracting by remember { mutableStateOf(false) }
    val labelColor = MaterialTheme.colorScheme.onSurface
    val dotColor = MaterialTheme.colorScheme.onSurface
    val labelBackgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
    val textMeasurer = rememberTextMeasurer()

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
        if (!isLoading) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        if (isInteractivityEnabled) {
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
                    }
            ) {
                val prices = list.map { it.price.toFloat() }
                val max = prices.maxOrNull() ?: 0f
                val min = prices.minOrNull() ?: 0f

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
                if (isInteractivityEnabled) {
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

                drawMinMaxLabels(
                    labelColor = labelColor,
                    labelBackgroundColor = labelBackgroundColor,
                    min = min,
                    max = max,
                    textMeasurer = textMeasurer
                )
            }

            // Overlay for selected data point information, only shown when interacting
            if (isInteractivityEnabled && isInteracting) {
                selectedIndex.let { index ->
                    val dataPoint = list[index]
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
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

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun DrawScope.drawMinMaxLabels(
    labelColor: Color,
    labelBackgroundColor: Color,
    min: Float,
    max: Float,
    textMeasurer: TextMeasurer
) {
    val padding = 8.dp.toPx()
    val verticalOffset = 16.dp.toPx()
    val arrowTextSpacing = 4.dp.toPx() // New variable for spacing between arrow and text
    val textStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        color = labelColor,
    )

    val minText = "$${formatToPrice(min.toDouble())}"
    val maxText = "$${formatToPrice(max.toDouble())}"

    val minTextLayout = textMeasurer.measure(minText, textStyle)
    val maxTextLayout = textMeasurer.measure(maxText, textStyle)

    val arrowSize = 16.dp.toPx()

    // Draw background rectangles for labels
    drawRoundRect(
        color = labelBackgroundColor,
        topLeft = Offset(0f, size.height - minTextLayout.size.height - padding * 2 - verticalOffset),
        size = Size(
            minTextLayout.size.width + padding * 2 + arrowSize + arrowTextSpacing,
            minTextLayout.size.height + padding * 2
        ),
        cornerRadius = CornerRadius(4.dp.toPx())
    )
    drawRoundRect(
        color = labelBackgroundColor,
        topLeft = Offset(size.width - maxTextLayout.size.width - padding * 2 - arrowSize - arrowTextSpacing, verticalOffset),
        size = Size(
            maxTextLayout.size.width + padding * 2 + arrowSize + arrowTextSpacing,
            maxTextLayout.size.height + padding * 2
        ),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    // Draw MIN label and arrow
    drawText(
        textLayoutResult = minTextLayout,
        topLeft = Offset(
            padding + arrowSize + arrowTextSpacing,
            size.height - minTextLayout.size.height - padding - verticalOffset
        ),
        color = labelColor
    )
    drawPath(
        path = Path().apply {
            moveTo(padding, size.height - minTextLayout.size.height / 2 - padding - verticalOffset)
            lineTo(padding + arrowSize / 2, size.height - padding - verticalOffset)
            lineTo(padding + arrowSize, size.height - minTextLayout.size.height / 2 - padding - verticalOffset)
            close()
        },
        color = Color.Red
    )

    // Draw MAX label and arrow
    drawText(
        textLayoutResult = maxTextLayout,
        topLeft = Offset(
            size.width - maxTextLayout.size.width - padding - arrowSize - arrowTextSpacing,
            padding + verticalOffset
        ),
        color = labelColor
    )
    drawPath(
        path = Path().apply {
            moveTo(size.width - padding, padding + maxTextLayout.size.height / 2 + verticalOffset)
            lineTo(size.width - padding - arrowSize / 2, padding + verticalOffset)
            lineTo(size.width - padding - arrowSize, padding + maxTextLayout.size.height / 2 + verticalOffset)
            close()
        },
        color = Color.Green
    )
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float {
    return if (max - min == 0f) 0f else (value - min) / (max - min)
}

private fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.monthNumber}/${dateTime.dayOfMonth}, ${dateTime.hour}:${dateTime.minute}"
}