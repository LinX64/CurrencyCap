package domain.repository

import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import org.mobilenativefoundation.store.store5.Store

interface CryptoRepository {

    fun fetchMarketChartDataNew(
        forceRefresh: Boolean = false,
        coinId: String,
        symbol: String,
        period: ChipPeriod = ChipPeriod.DAY
    ): Store<String, List<ChartDataPoint>>
}