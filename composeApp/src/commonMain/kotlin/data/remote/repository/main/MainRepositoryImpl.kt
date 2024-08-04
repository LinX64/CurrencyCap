package data.remote.repository.main

import data.remote.model.main.CryptoInfoDto
import data.remote.model.main.CurrenciesDto
import data.remote.model.main.toDomainModel
import data.util.APIConst.BASE_URL
import data.util.APIConst.CRYPTO_INFO_URL
import data.util.NetworkResult
import data.util.cacheDataOrFetchOnline
import data.util.parseCurrencyRates
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import domain.model.main.toEntity
import domain.repository.MainRepository
import domain.repository.RatesLocalDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class MainRepositoryImpl(
    private val httpClient: HttpClient,
    private val ratesLocalDataSource: RatesLocalDataSource,
) : MainRepository {

    override fun getAllRates(forceRefresh: Boolean): Flow<NetworkResult<Currencies>> = cacheDataOrFetchOnline(
        forceRefresh = forceRefresh,
        query = { ratesLocalDataSource.getRates() },
        fetch = { getParsedRates() },
        shouldFetch = { localRates -> localRates == null },
        clearLocalData = { ratesLocalDataSource.deleteRates() },
        saveFetchResult = { responseDto ->
            ratesLocalDataSource.insertRates(responseDto.toEntity())
        }
    )

    override fun getCryptoInfoBySymbol(
        forceRefresh: Boolean,
        symbol: String
    ): Flow<NetworkResult<CryptoInfo>> = cacheDataOrFetchOnline(
        query = { ratesLocalDataSource.getCryptoInfoBySymbol(symbol) },
        fetch = { getParsedCryptoBySymbol(symbol) },
        shouldFetch = { localCryptoInfo -> localCryptoInfo == null },
        forceRefresh = forceRefresh,
        clearLocalData = { ratesLocalDataSource.deleteCryptoInfoBySymbol(symbol) },
        saveFetchResult = { responseDto ->
            ratesLocalDataSource.insertCryptoInfo(responseDto.toEntity())
        }
    )

    private suspend fun getParsedCryptoBySymbol(symbol: String): CryptoInfo {
        val jsonString = httpClient.get("$CRYPTO_INFO_URL/$symbol").bodyAsText()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val partialResponse = json.decodeFromString<CryptoInfoDto>(jsonString)
        return partialResponse.toDomainModel()
    }

    private suspend fun getParsedRates(): CurrenciesDto {
        val plainResponse = httpClient.get(BASE_URL).bodyAsText()
        return parseCurrencyRates(plainResponse)
    }
}