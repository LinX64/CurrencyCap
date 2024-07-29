package domain.model

import domain.model.main.ChartDataPoint
import kotlinx.collections.immutable.ImmutableList

data class CryptoMarketChartData(
    val prices: ImmutableList<ChartDataPoint>
)