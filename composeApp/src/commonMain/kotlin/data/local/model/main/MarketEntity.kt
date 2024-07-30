package data.local.model.main

import domain.model.main.Market
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
open class MarketEntity : RealmObject {
    @PrimaryKey
    var baseId: String = ""
    var baseSymbol: String = ""
    var exchangeId: String = ""
    var percentExchangeVolume: String = ""
    var priceQuote: String = ""
    var priceUsd: String = ""
    var quoteId: String = ""
    var quoteSymbol: String = ""
    var rank: String = ""
    var tradesCount24Hr: String? = null
    var updated: Long = 0
    var volumeUsd24Hr: String = ""

    companion object
}

fun MarketEntity.toDomain(): Market {
    return Market(
        baseId = baseId,
        baseSymbol = baseSymbol,
        exchangeId = exchangeId,
        percentExchangeVolume = percentExchangeVolume,
        priceQuote = priceQuote,
        priceUsd = priceUsd,
        quoteId = quoteId,
        quoteSymbol = quoteSymbol,
        rank = rank,
        tradesCount24Hr = tradesCount24Hr,
        updated = updated,
        volumeUsd24Hr = volumeUsd24Hr
    )
}
