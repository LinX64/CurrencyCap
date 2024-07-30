package data.remote.repository.explore

import data.remote.model.main.CryptoDto
import data.remote.model.main.CurrenciesDto
import data.remote.model.main.toCryptoDomain
import data.util.APIConst.BASE_URL
import data.util.parseCurrencyRates
import data.util.retryOnIOException
import domain.model.main.Crypto
import domain.repository.ExploreRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExploreRepositoryImpl(
    private val httpClient: HttpClient,
) : ExploreRepository {

    override fun search(query: String): Flow<List<Crypto>> = flow {
        emit(searchWithEfficientAlgorithm(query).toCryptoDomain())
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    private suspend fun searchWithEfficientAlgorithm(query: String): List<CryptoDto> {
        val cryptoList = getCurrencies().crypto.sortedBy { it.symbol }

        val startIndex = cryptoList.binarySearch {
            it.symbol.compareTo(query, ignoreCase = true)
        }.let { if (it < 0) -(it + 1) else it }

        return cryptoList.subList(startIndex, cryptoList.size)
            .takeWhile { it.symbol.startsWith(query, ignoreCase = true) }
    }

    private suspend fun getCurrencies(): CurrenciesDto {
        val plainResponse = httpClient.get(BASE_URL).bodyAsText()
        return parseCurrencyRates(plainResponse)
    }
    // todo: see if we can have only one
}