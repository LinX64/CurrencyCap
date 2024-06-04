package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinCapRatesDao(
    @SerialName("data")
    val data: List<DataDao>,
    @SerialName("timestamp")
    val timestamp: Long
)

@Serializable
data class DataDao(
    @SerialName("currencySymbol")
    val currencySymbol: String? = null,
    @SerialName("id")
    val id: String,
    @SerialName("rateUsd")
    val rateUsd: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("type")
    val type: String
)

