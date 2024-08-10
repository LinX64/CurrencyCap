package data.local.model.main.detail

import domain.model.main.MarketData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
class MarketDataEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var currentPrice: Double = 0.0
    var high24h: Double = 0.0
    var low24h: Double = 0.0
    var priceChange24h: Double = 0.0
    var priceChangePercentage24h: Double = 0.0
    var priceChangePercentage7d: Double = 0.0
    var priceChangePercentage14d: Double = 0.0
    var priceChangePercentage30d: Double = 0.0
    var priceChangePercentage60d: Double = 0.0
    var priceChangePercentage200d: Double = 0.0
    var priceChangePercentage1y: Double = 0.0
    var marketCap: Double = 0.0
    var ath: Double = 0.0

    companion object
}

fun MarketDataEntity.toDomain(): MarketData {
    return MarketData(
        currentPrice = currentPrice,
        high24h = high24h,
        low24h = low24h,
        priceChange24h = priceChange24h,
        priceChangePercentage24h = priceChangePercentage24h,
        priceChangePercentage7d = priceChangePercentage7d,
        priceChangePercentage14d = priceChangePercentage14d,
        priceChangePercentage30d = priceChangePercentage30d,
        priceChangePercentage60d = priceChangePercentage60d,
        priceChangePercentage200d = priceChangePercentage200d,
        priceChangePercentage1y = priceChangePercentage1y,
        marketCap = currentPrice,
        ath = currentPrice,
    )
}