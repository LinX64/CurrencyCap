package data.local.model.main

import domain.model.main.Crypto
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
open class CryptoEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var ath: Double = 0.0
    var athChangePercentage: Double = 0.0
    var athDate: String = ""
    var atl: Double = 0.0
    var atlChangePercentage: Double = 0.0
    var atlDate: String = ""
    var circulatingSupply: Double = 0.0
    var currentPrice: Double = 0.0
    var high24h: Double = 0.0
    var image: String = ""
    var lastUpdated: String = ""
    var low24h: Double = 0.0
    var marketCap: Long = 0
    var marketCapChange24h: Double = 0.0
    var marketCapChangePercentage24h: Double = 0.0
    var marketCapRank: Int = 0
    var maxSupply: Double? = null
    var name: String = ""
    var priceChange24h: Double = 0.0
    var priceChangePercentage24h: Double = 0.0
    var symbol: String = ""
    var totalSupply: Double? = null
    var totalVolume: Double = 0.0

    companion object
}

fun CryptoEntity.toDomain(): Crypto {
    return Crypto(
        id = id,
        ath = ath,
        athChangePercentage = athChangePercentage,
        athDate = athDate,
        atl = atl,
        atlChangePercentage = atlChangePercentage,
        atlDate = atlDate,
        circulatingSupply = circulatingSupply,
        currentPrice = currentPrice,
        high24h = high24h,
        image = image,
        lastUpdated = lastUpdated,
        low24h = low24h,
        marketCap = marketCap,
        marketCapChange24h = marketCapChange24h,
        marketCapChangePercentage24h = marketCapChangePercentage24h,
        marketCapRank = marketCapRank,
        maxSupply = maxSupply,
        name = name,
        priceChange24h = priceChange24h,
        priceChangePercentage24h = priceChangePercentage24h,
        symbol = symbol,
        totalSupply = totalSupply,
        totalVolume = totalVolume
    )
}
