package data.remote.repository.main

import data.util.APIConst.COINCAP_BASE_URL
import domain.model.ChipPeriod
import domain.model.CoinCapResponse
import domain.model.CoinMarketChartData
import domain.model.PricePoint
import domain.repository.CryptoRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.days

class CryptoRepositoryImpl(
    private val httpClient: HttpClient
) : CryptoRepository {

    override fun fetchMarketChartData(
        coinId: String,
        period: ChipPeriod
    ): Flow<CoinMarketChartData> = flow {
        val endTime = Clock.System.now()
        val periodInDays = 7
        val startTime: Instant = endTime.minus(periodInDays.days)

        val response = httpClient.get("$COINCAP_BASE_URL/assets/bitcoin/history") {
            parameter("interval", "h1")
        }

        val json = Json { ignoreUnknownKeys = true }
        val jsonString = response.bodyAsText()
        val data = json.decodeFromString<CoinCapResponse>(jsonString)

        val chartData = data.data.map { dataPoint ->
            PricePoint(
                time = Instant.fromEpochMilliseconds(dataPoint.timestamp),
                price = dataPoint.price.toDouble()
            )
        }

        val coinMarketChartData = CoinMarketChartData(chartData)

        emit(coinMarketChartData)
    }.flowOn(Dispatchers.IO)
}