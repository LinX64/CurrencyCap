package data.local.model.main

import domain.model.main.BonbastRate
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
open class BonbastRateEntity : RealmObject {
    @PrimaryKey
    var code: String = ""
    var sell: Double = 0.0
    var buy: Double = 0.0

    companion object
}

fun BonbastRateEntity.toDomain(): BonbastRate {
    return BonbastRate(
        code = code,
        sell = sell,
        buy = buy,
        imageUrl = ""
    )
}
