package domain.repository

import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import kotlinx.coroutines.flow.Flow

interface MarketChartDataLocalRepository {

    fun getChartDataFromDb(
        symbol: String,
        period: ChipPeriod = ChipPeriod.DAY
    ): Flow<List<ChartDataPoint>>

    suspend fun insertMarketChartData(
        symbol: String,
        period: String,
        prices: List<ChartDataPoint>
    )
}