package data.remote.model.main

import domain.model.main.CryptoImage
import domain.model.main.CryptoInfo
import domain.model.main.Description
import domain.model.main.MarketData

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoInfoDto(
    @SerialName("id")
    val id: String,
    @SerialName("description")
    val descriptionDto: DescriptionDto,
    @SerialName("market_data")
    val marketDataDto: MarketDataDto,
    @SerialName("name")
    val name: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("image")
    val image: CryptoImageDto
)

@Serializable
data class DescriptionDto(
    @SerialName("en")
    val en: String
)

@Serializable
data class MarketDataDto(
    @SerialName("current_price")
    val currentPrice: Map<String, Double>,
    @SerialName("high_24h")
    val high24h: Map<String, Double>,
    @SerialName("low_24h")
    val low24h: Map<String, Double>,
    @SerialName("price_change_24h")
    val priceChange24h: Double,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerialName("price_change_percentage_7d")
    val priceChangePercentage7d: Double,
    @SerialName("price_change_percentage_14d")
    val priceChangePercentage14d: Double,
    @SerialName("price_change_percentage_30d")
    val priceChangePercentage30d: Double,
    @SerialName("price_change_percentage_60d")
    val priceChangePercentage60d: Double,
    @SerialName("price_change_percentage_200d")
    val priceChangePercentage200d: Double,
    @SerialName("price_change_percentage_1y")
    val priceChangePercentage1y: Double,
    @SerialName("market_cap")
    val marketCap: Map<String, Double>,
    @SerialName("ath")
    val ath: Map<String, Double>
)

@Serializable
data class CryptoImageDto(
    @SerialName("thumb")
    val thumb: String,
    @SerialName("small")
    val small: String,
    @SerialName("large")
    val large: String
)

internal fun CryptoInfoDto.toDomainModel(): CryptoInfo {
    return CryptoInfo(
        id = id,
        description = Description(en = descriptionDto.en),
        name = name,
        symbol = symbol,
        image = CryptoImage(
            thumb = image.thumb,
            small = image.small,
            large = image.large
        ),
        marketData = MarketData(
            currentPrice = marketDataDto.currentPrice["usd"] ?: 0.0,
            high24h = marketDataDto.high24h["usd"] ?: 0.0,
            low24h = marketDataDto.low24h["usd"] ?: 0.0,
            priceChange24h = marketDataDto.priceChange24h,
            priceChangePercentage24h = marketDataDto.priceChangePercentage24h,
            priceChangePercentage7d = marketDataDto.priceChangePercentage7d,
            priceChangePercentage14d = marketDataDto.priceChangePercentage14d,
            priceChangePercentage30d = marketDataDto.priceChangePercentage30d,
            priceChangePercentage60d = marketDataDto.priceChangePercentage60d,
            priceChangePercentage200d = marketDataDto.priceChangePercentage200d,
            priceChangePercentage1y = marketDataDto.priceChangePercentage1y,
            marketCap = marketDataDto.marketCap["usd"] ?: 0.0,
            ath = marketDataDto.ath["usd"] ?: 0.0
        )
    )
}