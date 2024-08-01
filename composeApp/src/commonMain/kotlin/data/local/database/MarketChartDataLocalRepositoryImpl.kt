package data.local.database

import data.local.model.ChartDataPointEntity
import data.local.model.MarketChartDataEntity
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.repository.MarketChartDataLocalRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MarketChartDataLocalRepositoryImpl(
    private val realm: Realm
) : MarketChartDataLocalRepository {

    override fun getChartDataFromDb(
        symbol: String,
        period: ChipPeriod
    ): Flow<List<ChartDataPoint>> {
        return realm.query<ChartDataPointEntity>()
            .query("symbol == $0 AND period == $1", symbol, period.name)
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
        symbol: String,
        period: String,
        prices: List<ChartDataPoint>
    ) {
        realm.writeBlocking {
            val marketChartData = query<MarketChartDataEntity>("symbol == $0 AND period == $1", symbol, period)
                .first()
                .find()
                ?: MarketChartDataEntity().apply {
                    this.symbol = symbol
                    this.period = period
                    this.data = realmListOf()
                }

            prices.forEach { price ->
                val chartDataPoint = ChartDataPointEntity().apply {
                    this.price = price.price
                    this.timestamp = price.timestamp
                }
                marketChartData.data.add(chartDataPoint)
            }

            copyToRealm(marketChartData, UpdatePolicy.ALL)
        }
    }
}