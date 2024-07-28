package domain.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class CoinGeckoResponse(val prices: List<List<Double>>)

data class PricePoint(val time: Instant, val price: Double)

data class CoinMarketChartData(val prices: List<PricePoint>)