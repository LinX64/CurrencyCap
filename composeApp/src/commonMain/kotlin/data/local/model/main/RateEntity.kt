package data.local.model.main

import domain.model.main.Rate
import io.realm.kotlin.types.RealmObject
import kotlinx.serialization.Serializable

@Serializable
open class RateEntity : RealmObject {
    var id: String = ""
    var rateUsd: String = ""
    var symbol: String = ""
    var type: String = ""
    var currencySymbol: String? = null

    companion object
}

fun RateEntity.toDomain(): Rate {
    return Rate(
        id = id,
        rateUsd = rateUsd,
        symbol = symbol,
        type = type,
        currencySymbol = currencySymbol
    )
}
