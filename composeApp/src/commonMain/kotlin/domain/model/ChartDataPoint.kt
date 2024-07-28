package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinCapResponse(
    val data: List<ChartDataPoint>,
    val timestamp: Long
)

@Serializable
data class ChartDataPoint(
    @SerialName("priceUsd")
    val price: String,
    @SerialName("time")
    val timestamp: Long,
)