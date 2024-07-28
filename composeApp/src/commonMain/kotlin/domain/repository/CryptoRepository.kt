package domain.repository

import domain.model.ChipPeriod
import domain.model.CoinMarketChartData
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun fetchMarketChartData(
        coinId: String,
        period: ChipPeriod
    ): Flow<CoinMarketChartData>
}