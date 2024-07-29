package domain.model.main

data class CryptoInfo(
    val id: String,
    val description: Description,
    val marketData: MarketData,
    val name: String,
    val symbol: String,
    val image: CryptoImage
)

data class Description(
    val en: String
)

data class MarketData(
    val currentPrice: CommonUsdPrice,
    val high24h: CommonUsdPrice,
    val low24h: CommonUsdPrice,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val priceChangePercentage14d: Double,
    val priceChangePercentage30d: Double,
    val priceChangePercentage60d: Double,
    val priceChangePercentage200d: Double,
    val priceChangePercentage1y: Double,
    val marketCap: CommonUsdPrice,
    val ath: CommonUsdPrice,
)

data class CommonUsdPrice(
    val usd: Double
)

data class CryptoImage(
    val thumb: String,
    val small: String,
    val large: String
)