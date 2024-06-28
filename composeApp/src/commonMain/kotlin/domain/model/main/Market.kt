package domain.model.main

data class Market(
    val baseId: String,
    val baseSymbol: String,
    val exchangeId: String,
    val percentExchangeVolume: String,
    val priceQuote: String,
    val priceUsd: String,
    val quoteId: String,
    val quoteSymbol: String,
    val rank: String,
    val tradesCount24Hr: String? = null,
    val updated: Long,
    val volumeUsd24Hr: String
)