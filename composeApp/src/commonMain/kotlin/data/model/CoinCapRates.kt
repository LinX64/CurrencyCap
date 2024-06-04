package data.model

import domain.model.DataDao
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinCapRates(
    @SerialName("data")
    val `data`: List<CoinCapData>,
    @SerialName("timestamp")
    val timestamp: Long
)

@Serializable
data class CoinCapData(
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

fun List<CoinCapData>.toDomain(): List<DataDao> {
    return map { it.toDomain() }
}

fun CoinCapData.toDomain(): DataDao {
    return DataDao(
        currencySymbol = currencySymbol,
        id = id,
        rateUsd = rateUsd,
        symbol = symbol,
        type = type
    )
}