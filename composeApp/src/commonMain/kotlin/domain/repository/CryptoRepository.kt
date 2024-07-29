package domain.repository

import domain.model.ChipPeriod
import domain.model.CryptoMarketChartData
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun fetchMarketChartData(
        coinId: String,
        symbol: String,
        period: ChipPeriod
    ): Flow<CryptoMarketChartData>
}