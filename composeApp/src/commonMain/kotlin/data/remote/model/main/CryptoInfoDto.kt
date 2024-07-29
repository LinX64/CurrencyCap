package data.remote.model.main

import domain.model.main.CommonUsdPrice
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
    val currentPrice: CommonUsdPriceDto,
    @SerialName("high_24h")
    val high24h: CommonUsdPriceDto,
    @SerialName("low_24h")
    val low24h: CommonUsdPriceDto,
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
    val marketCap: CommonUsdPriceDto,
    @SerialName("ath")
    val ath: CommonUsdPriceDto,
)

@Serializable
data class CommonUsdPriceDto(
    @SerialName("usd")
    val usd: Double
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

fun CryptoInfoDto.toDomainModel(): CryptoInfo {
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
            currentPrice = CommonUsdPrice(usd = 26.27),
            high24h = CommonUsdPrice(usd = 28.29),
            low24h = CommonUsdPrice(usd = 30.31),
            priceChange24h = 32.33,
            priceChangePercentage24h = 34.35,
            priceChangePercentage7d = 36.37,
            priceChangePercentage14d = 38.39,
            priceChangePercentage30d = 40.41,
            priceChangePercentage60d = 42.43,
            priceChangePercentage200d = 44.45,
            priceChangePercentage1y = 46.47,
            marketCap = CommonUsdPrice(usd = 48.49),
            ath = CommonUsdPrice(usd = 50.51)
        )
    )
}