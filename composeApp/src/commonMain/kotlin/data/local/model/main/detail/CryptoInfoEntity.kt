package data.local.model.main.detail

import domain.model.main.CryptoImage
import domain.model.main.CryptoInfo
import domain.model.main.Description
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
class CryptoInfoEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var description: String = ""
    var marketData: MarketDataEntity? = null
    var name: String = ""
    var symbol: String = ""
    var image: String = ""

    companion object
}

fun CryptoInfoEntity.toDomain(): CryptoInfo {
    return CryptoInfo(
        id = id,
        description = Description(en = description),
        marketData = marketData?.toDomain() ?: throw IllegalStateException("MarketData cannot be null"),
        name = name,
        symbol = symbol,
        image = CryptoImage(
            thumb = image,
            small = image,
            large = image
        )
    )
}