package data.remote.model.main

import data.local.model.main.MarketEntity
import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketDto(
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
) {
    fun toEntity() = MarketEntity().apply {
        baseId = this@MarketDto.baseId
        baseSymbol = this@MarketDto.baseSymbol
        exchangeId = this@MarketDto.exchangeId
        percentExchangeVolume = this@MarketDto.percentExchangeVolume
        priceQuote = this@MarketDto.priceQuote
        priceUsd = this@MarketDto.priceUsd
        quoteId = this@MarketDto.quoteId
        quoteSymbol = this@MarketDto.quoteSymbol
        rank = this@MarketDto.rank
        tradesCount24Hr = this@MarketDto.tradesCount24Hr
        updated = this@MarketDto.updated
        volumeUsd24Hr = this@MarketDto.volumeUsd24Hr
    }
}

fun List<MarketDto>.toEntity() = map { it.toEntity() }.toRealmList()