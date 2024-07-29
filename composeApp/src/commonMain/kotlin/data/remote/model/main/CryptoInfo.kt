package data.remote.model.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoInfo(
    @SerialName("id")
    val id: String,
    @SerialName("description")
    val description: Description,
    @SerialName("market_data")
    val marketData: MarketData,
    @SerialName("name")
    val name: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("image")
    val image: CryptoImage
)

@Serializable
data class Description(
    @SerialName("en")
    val en: String
)

@Serializable
data class MarketData(
    @SerialName("current_price")
    val currentPrice: CommonUsdPrice,
    @SerialName("high_24h")
    val high24h: CommonUsdPrice,
    @SerialName("low_24h")
    val low24h: CommonUsdPrice,
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
    val marketCap: CommonUsdPrice,
    @SerialName("ath")
    val ath: CommonUsdPrice,
)

@Serializable
data class CommonUsdPrice(
    @SerialName("usd")
    val usd: Double
)

@Serializable
data class CryptoImage(
    @SerialName("thumb")
    val thumb: String,
    @SerialName("small")
    val small: String,
    @SerialName("large")
    val large: String
)