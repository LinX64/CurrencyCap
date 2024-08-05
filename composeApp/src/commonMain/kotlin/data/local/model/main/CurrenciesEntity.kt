package data.local.model.main

import domain.model.main.Currencies
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
open class CurrenciesEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var timestamp: Long = 0
    var bonbast: RealmList<BonbastRateEntity> = realmListOf()
    var crypto: RealmList<CryptoEntity> = realmListOf()
    var markets: RealmList<MarketEntity> = realmListOf()
    var rates: RealmList<RateEntity> = realmListOf()

    companion object
}

fun CurrenciesEntity.toDomain(): Currencies {
    return Currencies(
        timestamp = timestamp,
        bonbast = bonbast.map { it.toDomain() },
        crypto = crypto.map { it.toDomain() },
        markets = markets.map { it.toDomain() },
        rates = rates.map { it.toDomain() }
    )
}