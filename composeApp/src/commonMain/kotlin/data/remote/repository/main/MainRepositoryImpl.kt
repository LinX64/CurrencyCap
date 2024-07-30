package data.remote.repository.main

import data.remote.model.main.CryptoInfoDto
import data.remote.model.main.CurrenciesDto
import data.remote.model.main.toDomain
import data.remote.model.main.toDomainModel
import data.util.APIConst.BASE_URL
import data.util.APIConst.CRYPTO_INFO_URL
import data.util.NetworkResult
import data.util.cacheDataOrFetchOnline
import data.util.parseCurrencyRates
import data.util.retryOnIOException
import domain.model.main.Crypto
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import domain.repository.MainRepository
import domain.repository.RatesLocalDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class MainRepositoryImpl(
    private val httpClient: HttpClient,
    private val ratesLocalDataSource: RatesLocalDataSource,
) : MainRepository {

    override fun getAllRates(): Flow<NetworkResult<Currencies>> = cacheDataOrFetchOnline(
        query = { ratesLocalDataSource.getRates() },
        fetch = { getCurrencies() },
        shouldFetch = { localRates -> localRates == null },
        saveFetchResult = { responseDto ->
            ratesLocalDataSource.insertRates(responseDto.toEntity())
        }
    )

    override fun getCryptoBySymbol(symbol: String): Flow<Crypto> = flow {
        val currencies = getCurrencies()

        val crypto = currencies.crypto.first { it.symbol == symbol }
        emit(crypto.toDomain())
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    override fun getCryptoInfoById(symbol: String): Flow<CryptoInfo> = flow {
        val jsonString = httpClient.get("$CRYPTO_INFO_URL/$symbol").bodyAsText()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val partialResponse = json.decodeFromString<CryptoInfoDto>(jsonString)
        emit(partialResponse.toDomainModel())
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    override suspend fun getCryptoNameBySymbol(symbol: String): String {
        val currencies = getCurrencies()

        val cryptoName = currencies.crypto.first { it.symbol == symbol }.name
        return cryptoName
    }

    private suspend fun getCurrencies(): CurrenciesDto {
        val plainResponse = httpClient.get(BASE_URL).bodyAsText()
        return parseCurrencyRates(plainResponse)
    }
}