package data.remote.model

import data.local.model.AssetPriceEntity
import domain.model.AssetPriceItem

data class AssetPriceItemDto(
    val symbol: String,
    val price: String,
    val previousPrice: String? = null
)

data class PriceDiff(
    val symbol: String,
    val currentPrice: Double,
    val previousPrice: Double?,
    val percentageChange: Double?
)

fun List<AssetPriceItem>.calculateDiff(): List<PriceDiff> {
    return this.map { current ->
        val currentPrice = current.price.toDoubleOrNull() ?: 0.0
        val previousPrice = current.previousPrice?.toDoubleOrNull()
        PriceDiff(
            symbol = current.symbol,
            currentPrice = currentPrice,
            previousPrice = previousPrice,
            percentageChange = if (previousPrice != null && previousPrice != 0.0) {
                (currentPrice - previousPrice) / previousPrice * 100
            } else null
        )
    }
}

internal fun AssetPriceItemDto.toDomain(): AssetPriceItem = AssetPriceItem(
    symbol = symbol,
    price = price,
    previousPrice = previousPrice
)

internal fun List<AssetPriceItemDto>.toEntity(): List<AssetPriceEntity> = map { dto ->
    AssetPriceEntity().apply {
        symbol = dto.symbol
        price = dto.price
        previousPrice = dto.previousPrice
    }
}