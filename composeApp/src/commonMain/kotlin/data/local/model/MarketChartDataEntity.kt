package data.local.model

import io.realm.kotlin.ext.realmListOf
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
    var data: RealmList<ChartDataPointEntity> = realmListOf()

    companion object
}

@Serializable
open class ChartDataPointEntity : RealmObject {
    var price: String = ""
    var timestamp: Long = 0
}
