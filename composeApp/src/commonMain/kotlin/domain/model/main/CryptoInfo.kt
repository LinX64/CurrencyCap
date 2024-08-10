package domain.model.main

import androidx.compose.runtime.Stable
import data.local.model.main.detail.CryptoInfoEntity
import data.local.model.main.detail.MarketDataEntity

@Stable
data class CryptoInfo(
    val id: String,
    val description: Description,
    val marketData: MarketData,
    val name: String,
    val symbol: String,
    val image: CryptoImage
)

@Stable
data class Description(
    val en: String
)

@Stable
data class MarketData(
    val currentPrice: Double,
    val high24h: Double,
    val low24h: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val priceChangePercentage14d: Double,
    val priceChangePercentage30d: Double,
    val priceChangePercentage60d: Double,
    val priceChangePercentage200d: Double,
    val priceChangePercentage1y: Double,
    val marketCap: Double,
    val ath: Double,
)

@Stable
data class CommonUsdPrice(val usd: Double)

@Stable
data class CryptoImage(
    val thumb: String,
    val small: String,
    val large: String
)

internal fun CryptoInfo.toEntity(): CryptoInfoEntity {
    return CryptoInfoEntity().apply {
        id = this@toEntity.id
        description = this@toEntity.description.en
        marketData = this@toEntity.marketData.toEntity()
        name = this@toEntity.name
        symbol = this@toEntity.symbol
        image = this@toEntity.image.large
    }
}

internal fun MarketData.toEntity(): MarketDataEntity {
    return MarketDataEntity().apply {
        currentPrice = this@toEntity.currentPrice
        high24h = this@toEntity.high24h
        low24h = this@toEntity.low24h
        priceChange24h = this@toEntity.priceChange24h
        priceChangePercentage24h = this@toEntity.priceChangePercentage24h
        priceChangePercentage7d = this@toEntity.priceChangePercentage7d
        priceChangePercentage14d = this@toEntity.priceChangePercentage14d
        priceChangePercentage30d = this@toEntity.priceChangePercentage30d
        priceChangePercentage60d = this@toEntity.priceChangePercentage60d
        priceChangePercentage200d = this@toEntity.priceChangePercentage200d
        priceChangePercentage1y = this@toEntity.priceChangePercentage1y
        marketCap = this@toEntity.marketCap
        ath = this@toEntity.ath
    }
}
