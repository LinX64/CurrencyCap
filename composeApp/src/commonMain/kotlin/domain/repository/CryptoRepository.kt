package domain.repository

import domain.model.ChipPeriod
import domain.model.CryptoMarketChartData
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun fetchMarketChartData(
        coinId: String,
        coinSymbol: String,
        period: ChipPeriod
    ): Flow<CryptoMarketChartData>
}