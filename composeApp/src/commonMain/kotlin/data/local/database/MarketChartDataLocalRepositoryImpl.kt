package data.local.database

import data.local.model.ChartDataPointEntity
import data.local.model.MarketChartDataEntity
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.repository.MarketChartDataLocalRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MarketChartDataLocalRepositoryImpl(
    private val realm: Realm
) : MarketChartDataLocalRepository {

    override fun getChartDataFromDb(
        coinId: String,
        symbol: String,
        period: ChipPeriod
    ): Flow<List<ChartDataPoint>> {
        return realm.query<ChartDataPointEntity>()
            .query("coinId == $0 AND symbol == $1 AND period == $2", coinId, symbol, period.name)
            .asFlow()
            .map { entities ->
                entities.list.map { entity ->
                    ChartDataPoint(
                        price = entity.price,
                        timestamp = entity.timestamp
                    )
                }
            }
    }

    override suspend fun insertMarketChartData(
        coinId: String,
        symbol: String,
        period: String,
        prices: List<ChartDataPoint>
    ) {
        realm.writeBlocking {
            prices.forEach { price ->
                copyToRealm(MarketChartDataEntity().apply {
                    this.id = coinId
                    this.symbol = symbol
                    this.period = period
                })
                copyToRealm(ChartDataPointEntity().apply {
                    this.price = price.price
                    this.timestamp = price.timestamp
                })
            }
        }
    }
}