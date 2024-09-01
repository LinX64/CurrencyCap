package data.local.database

import data.local.model.MarketChartDataEntity
import data.local.model.PriceDataPointEntity
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.repository.MarketChartDataLocalRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class MarketChartDataLocalRepositoryImpl(
    private val realm: Realm
) : MarketChartDataLocalRepository {

    override fun getChartDataFromDb(
        symbol: String,
        period: ChipPeriod
    ): Flow<List<ChartDataPoint>> {
        return realm.query<MarketChartDataEntity>(
            "symbol == $0 AND period == $1",
            symbol,
            period.interval
        )
            .asFlow()
            .map { results ->
                results.list.flatMap { entity ->
                    entity.chartData.map { dataPoint ->
                        ChartDataPoint(dataPoint.price, dataPoint.timestamp)
                    }
                }
            }
    }

    override suspend fun insertMarketChartData(
        symbol: String,
        period: String,
        prices: List<ChartDataPoint>
    ) {
        realm.write {
            val marketChartData = MarketChartDataEntity().apply {
                this.id = "${symbol}_${period}"
                this.symbol = symbol
                this.period = period
                this.chartData = prices.map { price ->
                    PriceDataPointEntity().apply {
                        this.price = price.price
                        this.timestamp = price.timestamp
                    }
                }.toRealmList()

                this.lastUpdated = Clock.System.now().toEpochMilliseconds()
            }

            copyToRealm(marketChartData, UpdatePolicy.ALL)
        }
    }

    override suspend fun deleteChartDataFromDb(key: String) {
        realm.write {
            val marketChartData = realm.query<MarketChartDataEntity>("id == $0", key).first()
            delete(marketChartData)
        }
    }
}