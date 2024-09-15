package domain.model

data class AssetPriceItem(
    val symbol: String,
    val price: String,
    val previousPrice: String? = null
)
