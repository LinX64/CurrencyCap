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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.no_chart_data_available
import domain.model.main.ChartDataPoint
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import ui.common.formatToPrice
import util.ChartHelper.drawChartLine
import util.ChartHelper.drawHighestPoint
import util.ChartHelper.drawMinMaxLabels
import util.ChartHelper.drawSelectedPointLine
import util.ChartHelper.formatTimestamp
import util.ChartState

@Composable
internal fun InteractiveCryptoChart(
    modifier: Modifier = Modifier,
    chartData: Set<ChartDataPoint>?,
    isInteractivityEnabled: Boolean = true,
    isLoading: Boolean = false,
    height: Dp = 200.dp,
    lighterColor: Color = MaterialTheme.colorScheme.surface,
    lightLineColor: Color = MaterialTheme.colorScheme.primary,
    labelColor: Color = MaterialTheme.colorScheme.onSurface,
    labelBackgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
    textMeasurer: TextMeasurer = rememberTextMeasurer()
) {
    val shouldShowEmptyState = chartData.isNullOrEmpty() && !isLoading
    var showNoDataMessage by remember { mutableStateOf(false) }

    LaunchedEffect(shouldShowEmptyState) {
        showNoDataMessage = false
        if (shouldShowEmptyState) {
            delay(3000)
            showNoDataMessage = true
        }
    }

    Box(
        modifier = modifier.fillMaxWidth()
            .then(modifier)
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            chartData?.isNotEmpty() == true -> {
                DrawChart(
                    modifier = Modifier.align(Alignment.Center).height(height),
                    chartData = chartData.toList(),
                    isInteractivityEnabled = isInteractivityEnabled,
                    lighterColor = lighterColor,
                    lightLineColor = lightLineColor,
                    labelColor = labelColor,
                    labelBackgroundColor = labelBackgroundColor,
                    textMeasurer = textMeasurer,
                )
            }

            else -> Unit
        }

        if (showNoDataMessage) {
            Text(
                text = stringResource(Res.string.no_chart_data_available),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun DrawChart(
    modifier: Modifier = Modifier,
    chartData: List<ChartDataPoint>,
    isInteractivityEnabled: Boolean,
    lighterColor: Color,
    lightLineColor: Color,
    labelColor: Color,
    labelBackgroundColor: Color,
    textMeasurer: TextMeasurer,
) {
    val chartState = rememberChartState(chartData)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(isInteractivityEnabled) {
                if (isInteractivityEnabled) {
                    detectDragGestures(
                        onDragStart = { chartState.startInteraction() },
                        onDragEnd = { chartState.endInteraction() },
                        onDragCancel = { chartState.endInteraction() },
                        onDrag = { change, _ ->
                            val x = change.position.x
                            chartState.updateSelectedIndex((x / size.width) * (chartData.size - 1))
                        }
                    )
                }
            }
    ) {
        drawChartLine(chartData, chartState, size, lighterColor, lightLineColor)

        if (isInteractivityEnabled) {
            drawHighestPoint(chartData, chartState, size)
            drawSelectedPointLine(chartState, size)
            drawMinMaxLabels(
                labelColor = labelColor,
                labelBackgroundColor = labelBackgroundColor,
                min = chartState.minPrice,
                max = chartState.maxPrice,
                textMeasurer = textMeasurer,
                size = size
            )
        }
    }

    if (isInteractivityEnabled && chartState.isInteracting) {
        SelectedDataPointOverlay(chartData[chartState.selectedIndex])
    }
}

@Composable
private fun rememberChartState(chartData: List<ChartDataPoint>): ChartState = remember(chartData) {
    ChartState(chartData)
}

@Composable
private fun SelectedDataPointOverlay(dataPoint: ChartDataPoint) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
            .padding(8.dp)
    ) {
        Text(
            text = "Price: $${formatToPrice(dataPoint.price.toDouble())}",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Time: ${formatTimestamp(dataPoint.timestamp)}",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
