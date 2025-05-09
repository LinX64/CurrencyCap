package data.remote.model.main

import data.local.model.main.CryptoEntity
import domain.model.main.Crypto
import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class CryptoDto(
    @SerialName("ath")
    val ath: Double,
    @SerialName("ath_change_percentage")
    val athChangePercentage: Double,
    @SerialName("ath_date")
    val athDate: String,
    @SerialName("atl")
    val atl: Double,
    @SerialName("atl_change_percentage")
    val atlChangePercentage: Double,
    @SerialName("atl_date")
    val atlDate: String,
    @SerialName("circulating_supply")
    val circulatingSupply: Double,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("fully_diluted_valuation")
    var fullyDilutedValuation: Long? = null,
    @SerialName("high_24h")
    val high24h: Double,
    @SerialName("id")
    val id: String,
    @SerialName("image")
    val image: String,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("low_24h")
    val low24h: Double,
    @SerialName("market_cap")
    val marketCap: Long,
    @SerialName("market_cap_change_24h")
    val marketCapChange24h: Double,
    @SerialName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,
    @SerialName("market_cap_rank")
    val marketCapRank: Int,
    @SerialName("max_supply")
    val maxSupply: Double? = null,
    @SerialName("name")
    val name: String,
    @SerialName("price_change_24h")
    val priceChange24h: Double,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerialName("roi")
    val roi: JsonObject? = null,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("total_supply")
    val totalSupply: Double? = null,
    @SerialName("total_volume")
    val totalVolume: Double
) {
    fun toEntity() = CryptoEntity().apply {
        ath = this@CryptoDto.ath
        athChangePercentage = this@CryptoDto.athChangePercentage
        athDate = this@CryptoDto.athDate
        atl = this@CryptoDto.atl
        atlChangePercentage = this@CryptoDto.atl
        atlDate = this@CryptoDto.atlDate
        circulatingSupply = this@CryptoDto.circulatingSupply
        currentPrice = this@CryptoDto.currentPrice
        high24h = this@CryptoDto.high24h
        id = this@CryptoDto.id
        image = this@CryptoDto.image
        lastUpdated = this@CryptoDto.lastUpdated
        low24h = this@CryptoDto.low24h
        marketCap = this@CryptoDto.marketCap
        marketCapChange24h = this@CryptoDto.marketCapChange24h
        marketCapChangePercentage24h = this@CryptoDto.marketCapChangePercentage24h
        marketCapRank = this@CryptoDto.marketCapRank
        maxSupply = this@CryptoDto.maxSupply
        name = this@CryptoDto.name
        priceChange24h = this@CryptoDto.priceChange24h
        priceChangePercentage24h = this@CryptoDto.priceChangePercentage24h
        symbol = this@CryptoDto.symbol
        totalSupply = this@CryptoDto.totalSupply
        totalVolume = this@CryptoDto.totalVolume
    }
}

fun CryptoDto.toDomain() = Crypto(
    ath = ath,
    athChangePercentage = athChangePercentage,
    athDate = athDate,
    atl = atl,
    atlChangePercentage = atlChangePercentage,
    atlDate = atlDate,
    circulatingSupply = circulatingSupply,
    currentPrice = currentPrice,
    fullyDilutedValuation = fullyDilutedValuation,
    high24h = high24h,
    id = id,
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

fun List<CryptoDto>.toEntity() = map { it.toEntity() }.toRealmList()
