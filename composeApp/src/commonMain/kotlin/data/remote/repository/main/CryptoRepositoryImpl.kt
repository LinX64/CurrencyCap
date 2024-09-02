package data.remote.repository.main

import data.remote.model.main.ChartDataPointDto
import data.remote.model.main.CoinCapChartResponseDto
import data.remote.model.main.toChartDataPoints
import data.remote.model.requests.History
import data.util.parseResponse
import di.coinCapApi
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.repository.CryptoRepository
import domain.repository.MarketChartDataLocalRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path
import kotlinx.datetime.Instant
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

class CryptoRepositoryImpl(
    private val httpClient: HttpClient,
    private val marketChartDataLocalRepository: MarketChartDataLocalRepository
) : CryptoRepository {

    override fun fetchMarketChartDataNew(
        forceRefresh: Boolean,
        coinId: String,
        symbol: String,
        period: ChipPeriod
    ): Store<String, List<ChartDataPoint>> {
        return StoreBuilder.from(
            fetcher = Fetcher.of { fetchDataBy(coinId, symbol, period) },
            sourceOfTruth = SourceOfTruth.of(
                reader = { marketChartDataLocalRepository.getChartDataFromDb(symbol, period) },
                writer = { _, chartData ->
                    marketChartDataLocalRepository.insertMarketChartData(
                        symbol = symbol,
                        period = period.interval,
                        prices = chartData
                    )
                },
                delete = { key: String -> marketChartDataLocalRepository.deleteChartDataFromDb(key) }
            )
        ).build()
    }

    private suspend fun fetchDataBy(
        id: String,
        symbol: String? = null, // TODO: Add fallback with symbol
        period: ChipPeriod
    ): List<ChartDataPoint> {
        val coinCapResponse = httpClient.get(History()) {
            coinCapApi()
            url {
                path("assets", id, "history")
                parameter("interval", period.interval)
            }
        }
        return processChartDataResp(coinCapResponse)
    }

    private suspend fun processChartDataResp(response: HttpResponse): List<ChartDataPoint> {
        val data = parseResponse<CoinCapChartResponseDto>(response)

        val chartData = data.data.map { dataPoint ->
            ChartDataPointDto(
                price = dataPoint.price,
                timestamp = Instant.fromEpochMilliseconds(dataPoint.timestamp).toEpochMilliseconds()
            )
        }

        return chartData.toChartDataPoints()
    }
}