package domain.repository

import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import kotlinx.coroutines.flow.Flow

interface MarketChartDataLocalRepository {

    fun getChartDataFromDb(
        coinId: String,
        symbol: String,
        period: ChipPeriod
    ): Flow<List<ChartDataPoint>>

    suspend fun insertMarketChartData(
        coinId: String,
        symbol: String,
        period: String,
        prices: List<ChartDataPoint>
    )
}