package data.remote.repository.main

import data.util.APIConst.CRYPTO_MARKET_URL
import domain.model.ChipPeriod
import domain.model.CoinGeckoResponse
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
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

class CryptoRepositoryImpl(
    private val httpClient: HttpClient
) : CryptoRepository {

    override fun fetchMarketChartData(
        coinId: String,
        period: ChipPeriod
    ): Flow<CoinMarketChartData> = flow {
        val response = httpClient.get("$CRYPTO_MARKET_URL/$coinId/$MARKET_CHART") {
            parameter("vs_currency", "usd")
            parameter("days", period.days)
        }
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = response.bodyAsText()
        val data = json.decodeFromString<CoinGeckoResponse>(jsonString)

        val chartData = data.prices.map { (timestamp, price) ->
            PricePoint(
                time = Instant.fromEpochMilliseconds(timestamp.toLong()),
                price = price
            )
        }

        val coinMarketChartData = CoinMarketChartData(chartData)

        emit(coinMarketChartData)
    }.flowOn(Dispatchers.IO)

    private companion object {
        const val MARKET_CHART = "market_chart"
    }
}