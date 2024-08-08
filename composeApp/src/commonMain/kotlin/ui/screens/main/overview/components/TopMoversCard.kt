package ui.screens.main.overview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.main.Crypto
import kotlinx.collections.immutable.persistentListOf
import ui.common.formatToPrice
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_32

@Composable
internal fun TopMoversCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    topMovers: Crypto,
    onClick: (id: String, symbol: String) -> Unit,
) {
    val isLoadingModifier = if (isLoading) Modifier else Modifier

    Card(
        modifier = modifier
            .width(170.dp)
            .height(250.dp),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        onClick = { onClick(topMovers.id, topMovers.symbol) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF4A148C), Color(0xFF000000)),
                        startY = 0f,
                        endY = 250f
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFB39DDB).copy(alpha = 0.5f),
                                Color.Transparent
                            ),
                            center = Offset(200f, 55f),
                            radius = 120f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .background(
                            if (topMovers.priceChange24h >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${if (topMovers.priceChange24h >= 0) "+" else ""}${formatToPrice(topMovers.priceChange24h)}%",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = isLoadingModifier,
                    text = topMovers.symbol,
                    color = Color(0xFFB39DDB),
                    fontSize = 14.sp
                )

                Text(
                    modifier = isLoadingModifier,
                    text = topMovers.name,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = isLoadingModifier,
                    text = "$${formatToPrice(topMovers.currentPrice)}",
                    color = Color.Gray.copy(alpha = 0.5f),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(SPACER_PADDING_32))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    TopMoversChart(
                        modifier = Modifier.fillMaxSize(),
                        list = persistentListOf(
                            topMovers.currentPrice.toFloat(),
                            topMovers.high24h.toFloat(),
                            topMovers.currentPrice.toFloat(),
                            topMovers.currentPrice.toFloat(),
                            topMovers.currentPrice.toFloat(),
                            topMovers.low24h.toFloat(),
                            topMovers.currentPrice.toFloat(),
                            topMovers.high24h.toFloat(),
                            topMovers.currentPrice.toFloat(),
                            topMovers.high24h.toFloat(),
                        )
                    )
                }
            }
        }
    }
}
