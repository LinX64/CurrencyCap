package domain.model.main

import data.remote.model.main.ChartDataPointDto
import kotlinx.collections.immutable.ImmutableList

data class CoinCapChartResponse(
    val data: ImmutableList<ChartDataPointDto>,
    val timestamp: Long
)

data class ChartDataPoint(
    val price: String,
    val timestamp: Long,
)
