package domain.model

import domain.model.main.ChartDataPoint

data class CryptoMarketChartData(
    val prices: List<ChartDataPoint>
)