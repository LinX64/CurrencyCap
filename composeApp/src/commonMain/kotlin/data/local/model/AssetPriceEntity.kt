package data.local.model

import domain.model.AssetPriceItem
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
open class AssetPriceEntity : RealmObject {
    @PrimaryKey
    var symbol: String = ""
    var price: String = ""
    var previousPrice: String? = null

    companion object
}

fun AssetPriceEntity.toDomain(): AssetPriceItem = AssetPriceItem(
    symbol = symbol,
    price = price,
    previousPrice = previousPrice
)
