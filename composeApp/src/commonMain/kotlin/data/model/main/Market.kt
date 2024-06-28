package data.model.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Market(
    @SerialName("baseId")
    val baseId: String,
    @SerialName("baseSymbol")
    val baseSymbol: String,
    @SerialName("exchangeId")
    val exchangeId: String,
    @SerialName("percentExchangeVolume")
    val percentExchangeVolume: String,
    @SerialName("priceQuote")
    val priceQuote: String,
    @SerialName("priceUsd")
    val priceUsd: String,
    @SerialName("quoteId")
    val quoteId: String,
    @SerialName("quoteSymbol")
    val quoteSymbol: String,
    @SerialName("rank")
    val rank: String,
    @SerialName("tradesCount24Hr")
    val tradesCount24Hr: String? = null,
    @SerialName("updated")
    val updated: Long,
    @SerialName("volumeUsd24Hr")
    val volumeUsd24Hr: String
)