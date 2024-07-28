package com.client.currencycap

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

//@Composable
//@Preview(showBackground = true)
//private fun ExchangePreview() {
//    val hazeState = remember { HazeState() }
//
//    KoinPreview {
//        Column {
//            OverviewScreen(
//                state = OverviewState.Success(
//                    bonbastRates = getDummyBonbastRates(),
//                    cryptoRates = getDummyCryptoItems(),
//                    markets = getDummyMarketRates(),
//                    fiatRates = getDummyRatesItem(),
//                    news = listOf(getDummyNewsItem()),
//                    topMovers = getDummyCryptoItems()
//                ),
//                hazeState = hazeState,
//                onNewsItemClick = { /*TODO*/ },
//                padding = PaddingValues(0.dp),
//                onSearchCardClicked = {},
//                onCircleButtonClicked = {},
//                onRetry = {},
//            )
//        }
//    }
//}

//@Composable
//@Preview(showBackground = true)
//private fun ButtonsPreview() {
//    KoinPreview {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            PrimaryButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            PrimarySmallIconButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            SecondaryButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            SecondarySmallIconButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//        }
//    }
//}

@Composable
@Preview(showBackground = true)
private fun LineChartPreview() {
    KoinPreview {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LineChart(
                graphColor = Color.Red,
                showDashedLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                data = persistentListOf(DataPoint(1.0, "1", "1")),
                showYLabels = true
            )
        }
    }
}

data class DataPoint(
    val y: Double,
    val xLabel: String?,
    val yLabel: String?
)

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: ImmutableList<DataPoint>,
    graphColor: Color,
    showDashedLine: Boolean,
    showYLabels: Boolean = false
) {
    val spacing = 0f
    val transparentGraphColor = remember(key1 = graphColor) {
        graphColor.copy(alpha = 0.5f)
    }

    val (lowerValue, upperValue) = remember(key1 = data) {
        Pair(
            data.minBy { it.y },
            data.maxBy { it.y }
        )
    }

    val density = LocalDensity.current
    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size

        var lastX = 0f
        var firstY = 0f
        val strokePath = Path().apply {
            val height = size.height
            for (i in data.indices) {
                val info = data[i]
                val nextInfo = data.getOrNull(i + 1) ?: data.last()
                val leftRatio = (info.y - lowerValue.y) / (upperValue.y - lowerValue.y)
                val rightRatio = (nextInfo.y - lowerValue.y) / (upperValue.y - lowerValue.y)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (leftRatio * height).toFloat()

                if (i == 0) {
                    firstY = y1
                }

                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticTo(
                    x1, y1, lastX, (y1 + y2) / 2f
                )
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            ),
        )

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        if (showDashedLine) {
            val dottedPath = Path().apply {
                moveTo(0f, firstY)
                lineTo(lastX, firstY)
            }

            drawPath(
                path = dottedPath,
                color = graphColor.copy(alpha = .8f),
                style = Stroke(
                    width = 1.5.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f), 0f)
                )
            )
        }
        val StocksDarkPrimaryText = Color(0xFFEBEBEB)

        if (showYLabels) {
            val textPaint = Paint().apply {
                color = StocksDarkPrimaryText.toArgb()
                textAlign = Paint.Align.RIGHT
                textSize = density.run { 12.dp.toPx() }
                typeface = setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
                alpha = 192
            }

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "MAX ${upperValue.yLabel.orEmpty()}",
                    size.width - 16.dp.toPx(),
                    0 + 8.dp.toPx(),
                    textPaint
                )
                drawText(
                    "MIN ${lowerValue.yLabel.orEmpty()}",
                    size.width - 16.dp.toPx(),
                    size.height - 4.dp.toPx(),
                    textPaint
                )
            }
        }
    }
}


