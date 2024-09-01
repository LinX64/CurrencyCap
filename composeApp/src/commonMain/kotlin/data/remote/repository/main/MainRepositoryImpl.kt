package data.remote.repository.main

import data.remote.model.main.CryptoInfoDto
import data.remote.model.main.CurrenciesDto
import data.remote.model.main.toDomainModel
import data.remote.model.requests.GetCurrencies
import data.util.parseCurrencyRates
import data.util.parseResponse
import di.baseApi
import di.coinGeckoApi
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import domain.model.main.toEntity
import domain.repository.MainRepository
import domain.repository.RatesLocalDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.path
import io.realm.kotlin.internal.platform.pathOf
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

class MainRepositoryImpl(
    private val httpClient: HttpClient,
    private val ratesLocalDataSource: RatesLocalDataSource,
) : MainRepository {

    override fun getAllRatesNew(): Store<String, Currencies> {
        return StoreBuilder.from(
            fetcher = Fetcher.of { key: String -> getParsedRates() },
            sourceOfTruth = SourceOfTruth.of(
                reader = { _: String -> ratesLocalDataSource.getRates() },
                writer = { _: String, responseDto: CurrenciesDto ->
                    ratesLocalDataSource.insertRates(responseDto.toEntity())
                },
                deleteAll = { ratesLocalDataSource.deleteRates() }
            )
        ).build()
    }

    override fun getCryptoInfoBySymbolNew(
        id: String,
        symbol: String
    ): Store<Any, CryptoInfo> {
        return StoreBuilder.from(
            fetcher = Fetcher.of { getParsedCryptoBy(id, symbol) },
            sourceOfTruth = SourceOfTruth.of(
                reader = { ratesLocalDataSource.getCryptoInfoBySymbol(symbol) },
                writer = { _, responseDto -> ratesLocalDataSource.insertCryptoInfo(responseDto.toEntity()) },
                deleteAll = { ratesLocalDataSource.deleteCryptoInfoBySymbol(symbol) }
            )
        ).build()
    }

    private suspend fun getParsedCryptoBy(
        id: String,
        symbol: String
    ): CryptoInfo {
        val symbolResponse: HttpResponse = httpClient.get {
            coinGeckoApi()
            url { pathOf("symbol", symbol) }
        }

        return when (symbolResponse.status) {
            HttpStatusCode.OK -> {
                val partialResponse = parseResponse<CryptoInfoDto>(symbolResponse)
                partialResponse.toDomainModel()
            }

            else -> {
                val jsonString = httpClient.get {
                    coinGeckoApi()
                    url { path("coins", id) }
                }

                val partialResponse = parseResponse<CryptoInfoDto>(jsonString)
                partialResponse.toDomainModel()
            }
        }
    }

    private suspend fun getParsedRates(): CurrenciesDto {
        val plainResponse = httpClient.get(GetCurrencies()) { baseApi() }
        return parseCurrencyRates(plainResponse.bodyAsText())
    }
}