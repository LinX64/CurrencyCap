package data.remote.repository.main

import data.remote.model.main.ChartDataPointDto
import data.remote.model.main.CoinCapChartResponseDto
import data.remote.model.main.toChartDataPoints
import data.util.APIConst.COINCAP_BASE_URL
import data.util.NetworkResult
import data.util.cacheDataOrFetchOnline
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.repository.CryptoRepository
import domain.repository.MarketChartDataLocalRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

class CryptoRepositoryImpl(
    private val httpClient: HttpClient,
    private val marketChartDataLocalRepository: MarketChartDataLocalRepository
) : CryptoRepository {

    override fun fetchMarketChartData(
        coinId: String,
        symbol: String,
        period: ChipPeriod
    ): Flow<NetworkResult<List<ChartDataPoint>>> = cacheDataOrFetchOnline(
        forceRefresh = false,
        query = { marketChartDataLocalRepository.getChartDataFromDb(symbol, period) },
        fetch = { fetchDataWithFilter(coinId, symbol, period) },
        shouldFetch = { localChartData -> localChartData.isNullOrEmpty() },
        clearLocalData = { },
        isFresh = { localData ->
            localData != null && (localData as? Collection<*>)?.isNotEmpty() == true
        },
        saveFetchResult = { chartData ->
            if (chartData.isNotEmpty()) {
                marketChartDataLocalRepository.insertMarketChartData(symbol, period.interval, chartData)
            }
        }
    )

    private suspend fun fetchDataWithFilter(
        coinId: String? = null,
        symbol: String? = null,
        period: ChipPeriod = ChipPeriod.DAY
    ): List<ChartDataPoint> {
        val response = fetchData(id = coinId, period = period)
        when (response.status) {
            HttpStatusCode.OK -> {
                val chartData = processResponse(response)
                return chartData
            }

            HttpStatusCode.NotFound -> {
                // sometimes this works, sometimes no TODO: save only prices if available
                val responseWithSymbol = fetchData(symbol = symbol, period = period)
                val chartData = processResponse(responseWithSymbol)
                return chartData
            }

            else -> return processResponse(response)
        }
    }

    private suspend fun fetchData(
        id: String? = null,
        symbol: String? = null,
        period: ChipPeriod = ChipPeriod.DAY
    ): HttpResponse {
        return if (symbol?.isNotEmpty() == true) {
            httpClient.get("$COINCAP_BASE_URL/assets/$symbol/history") {
                parameter("interval", period.interval)
            }
        } else httpClient.get("$COINCAP_BASE_URL/assets/$id/history") {
            parameter("interval", period.interval)
        }
    }

    private suspend fun processResponse(response: HttpResponse): List<ChartDataPoint> {
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = response.bodyAsText()
        val data = json.decodeFromString<CoinCapChartResponseDto>(jsonString)

        val chartData = data.data.map { dataPoint ->
            ChartDataPointDto(
                price = dataPoint.price,
                timestamp = Instant.fromEpochMilliseconds(dataPoint.timestamp).toEpochMilliseconds(),
            )
        }

        return chartData.toChartDataPoints()
    }
}