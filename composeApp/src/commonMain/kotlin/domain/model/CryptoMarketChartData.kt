package domain.model

import androidx.compose.runtime.Stable
import domain.model.main.ChartDataPoint

@Stable
data class CryptoMarketChartData(
    val prices: List<ChartDataPoint>
)