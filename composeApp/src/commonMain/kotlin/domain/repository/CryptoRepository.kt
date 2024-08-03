package domain.repository

import data.util.NetworkResult
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    fun fetchMarketChartData(
        forceRefresh: Boolean = false,
        coinId: String,
        symbol: String,
        period: ChipPeriod = ChipPeriod.DAY
    ): Flow<NetworkResult<List<ChartDataPoint>>>
}