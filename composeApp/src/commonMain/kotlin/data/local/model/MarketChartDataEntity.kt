package data.local.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
open class MarketChartDataEntity : RealmObject {
    @PrimaryKey
    var id: String = ""
    var symbol: String = ""
    var period: String = ""
    var chartData: RealmList<PriceDataPointEntity> = realmListOf()
    var lastUpdated: Long = 0

    companion object
}

@Serializable
open class PriceDataPointEntity : EmbeddedRealmObject {
    var price: String = ""
    var timestamp: Long = 0

    companion object
}