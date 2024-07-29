package data.remote.repository.main

import data.remote.model.main.ChartDataPointDto
import data.remote.model.main.CoinCapChartResponseDto
import data.remote.model.main.toChartDataPoints
import data.util.APIConst.COINCAP_BASE_URL
import domain.model.ChipPeriod
import domain.model.CryptoMarketChartData
import domain.repository.CryptoRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

class CryptoRepositoryImpl(
    private val httpClient: HttpClient
) : CryptoRepository {

    override fun fetchMarketChartData(
        coinId: String,
        symbol: String,
        period: ChipPeriod
    ): Flow<CryptoMarketChartData> = flow {

        val response = fetchData(id = coinId, period = period)
        when {
            response.status.isSuccess() -> emit(processResponse(response))
            response.status == HttpStatusCode.NotFound -> {
                fetchData(symbol = symbol, period = period).let {
                    if (it.status.isSuccess()) emit(processResponse(it))
                    else throw Exception("Failed to fetch data")
                }
            }

            else -> throw Exception("Failed to fetch data")
        }
    }.flowOn(Dispatchers.IO)

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

    private suspend fun processResponse(response: HttpResponse): CryptoMarketChartData {
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = response.bodyAsText()
        val data = json.decodeFromString<CoinCapChartResponseDto>(jsonString)

        val chartData = data.data.map { dataPoint ->
            ChartDataPointDto(
                price = dataPoint.price,
                timestamp = Instant.fromEpochMilliseconds(dataPoint.timestamp).toEpochMilliseconds(),
            )
        }

        return CryptoMarketChartData(prices = chartData.toChartDataPoints())
    }
}