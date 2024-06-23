package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCurrencies(
    @SerialName("bonbast")
    val bonbast: List<BonbastRate>,
    @SerialName("crypto")
    val crypto: List<Crypto>,
    @SerialName("markets")
    val markets: List<Market>,
    @SerialName("rates")
    val rates: List<Rate>
)

@Serializable
data class Crypto(
    @SerialName("Algorithm")
    val algorithm: String,
    @SerialName("AssetLaunchDate")
    val assetLaunchDate: String,
    @SerialName("BlockNumber")
    val blockNumber: Int,
    @SerialName("BlockReward")
    val blockReward: Double,
    @SerialName("BlockTime")
    val blockTime: Double,
    @SerialName("DocumentType")
    val documentType: String,
    @SerialName("FullName")
    val fullName: String,
    @SerialName("Id")
    val id: String,
    @SerialName("ImageUrl")
    val imageUrl: String,
    @SerialName("Internal")
    val `internal`: String,
    @SerialName("MaxSupply")
    val maxSupply: Double,
    @SerialName("Name")
    val name: String,
    @SerialName("NetHashesPerSecond")
    val netHashesPerSecond: Long,
    @SerialName("ProofType")
    val proofType: String,
    @SerialName("Rating")
    val rating: Rating,
    @SerialName("Type")
    val type: Int,
    @SerialName("Url")
    val url: String
)

@Serializable
data class Rate(
    @SerialName("id")
    val id: String,
    @SerialName("rateUsd")
    val rateUsd: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("type")
    val type: String,
    @SerialName("currencySymbol")
    val currencySymbol: String? = null
)

@Serializable
data class Market(
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
    val tradesCount24Hr: String,
    @SerialName("updated")
    val updated: Long,
    @SerialName("volumeUsd24Hr")
    val volumeUsd24Hr: String
)

@Serializable
data class Rating(
    @SerialName("Weiss")
    val weiss: Weiss
)

@Serializable
data class Weiss(
    @SerialName("MarketPerformanceRating")
    val marketPerformanceRating: String,
    @SerialName("Rating")
    val rating: String,
    @SerialName("TechnologyAdoptionRating")
    val technologyAdoptionRating: String
)