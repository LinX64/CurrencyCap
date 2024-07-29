package data.remote.model.main

import domain.model.main.ChartDataPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinCapChartResponseDto(
    val data: List<ChartDataPointDto>,
    val timestamp: Long
)

@Serializable
data class ChartDataPointDto(
    @SerialName("priceUsd")
    val price: String,
    @SerialName("time")
    val timestamp: Long,
)

fun ChartDataPointDto.toChartDataPoint(): ChartDataPoint {
    return ChartDataPoint(
        price = price,
        timestamp = timestamp
    )
}

fun List<ChartDataPointDto>.toChartDataPoints(): List<ChartDataPoint> {
    return map { it.toChartDataPoint() }
}