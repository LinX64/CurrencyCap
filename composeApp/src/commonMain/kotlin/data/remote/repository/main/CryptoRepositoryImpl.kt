package data.remote.repository.main

import data.remote.model.main.ChartDataPointDto
import data.remote.model.main.CoinCapChartResponseDto
import data.remote.model.main.CoinGeckoItemsResponse
import data.remote.model.main.toChartDataPoints
import data.util.APIConst.COINCAP_BASE_URL
import data.util.parseListResponse
import data.util.parseResponse
import di.coinGeckoApi
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.repository.CryptoRepository
import domain.repository.MarketChartDataLocalRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
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
            fetcher = Fetcher.of { key: String -> fetchDataWithFilter(coinId, symbol, period) },
            sourceOfTruth = SourceOfTruth.of(
                reader = { marketChartDataLocalRepository.getChartDataFromDb(symbol, period) },
                writer = { _, chartData ->
                    marketChartDataLocalRepository.insertMarketChartData(
                        symbol,
                        period.interval,
                        chartData
                    )
                },
                delete = { key: String -> marketChartDataLocalRepository.deleteChartDataFromDb(key) }
            )
        ).build()
    }

    private suspend fun fetchDataWithFilter(
        coinId: String,
        symbol: String,
        period: ChipPeriod = ChipPeriod.DAY
    ): List<ChartDataPoint> {
        val coinList = getCoinList()
        val foundCoinId = coinList.find { it.id == symbol }?.id
        val response = fetchDataBy(id = foundCoinId, period = period)

        return when (response.status) {
            HttpStatusCode.OK -> processResponse(response)
            HttpStatusCode.NotFound -> {
                val responseWithId = fetchDataBy(id = coinId, period = period)
                processResponse(responseWithId)
            }

            else -> processResponse(response)
        }
    }

    private suspend fun fetchDataBy(
        id: String? = null,
        period: ChipPeriod = ChipPeriod.DAY
    ): HttpResponse {
        return httpClient.get("$COINCAP_BASE_URL/assets/$id/history") {
            parameter("interval", period.interval)
        }
    }

    private suspend fun processResponse(response: HttpResponse): List<ChartDataPoint> {
        val data = parseResponse<CoinCapChartResponseDto>(response)

        val chartData = data.data.map { dataPoint ->
            ChartDataPointDto(
                price = dataPoint.price,
                timestamp = Instant.fromEpochMilliseconds(dataPoint.timestamp)
                    .toEpochMilliseconds(),
            )
        }

        return chartData.toChartDataPoints()
    }

    private suspend fun getCoinList(): List<CoinGeckoItemsResponse> {
        val request = httpClient.get {
            coinGeckoApi()
            url {
                path("coins", "list")
            }
        }
        return parseListResponse<CoinGeckoItemsResponse>(request)
    }
}