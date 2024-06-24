package data.repository.main

import data.model.toBonbastRateDomain
import data.model.toCryptoDomain
import data.model.toMarketDomain
import data.model.toRateDomain
import data.util.APIConst.BASE_URL
import data.util.parseCurrencyRates
import data.util.retryOnIOException
import domain.model.CurrenciesDto
import domain.model.RateDto
import domain.repository.MainRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl(
    private val httpClient: HttpClient
) : MainRepository {

    override fun getAllRates(): Flow<CurrenciesDto> = flow {
        val plainResponse = httpClient.get(BASE_URL).body<String>()
        val currencies = parseCurrencyRates(plainResponse)

        emit(
            CurrenciesDto(
                bonbast = currencies.bonbast.toBonbastRateDomain(),
                crypto = currencies.crypto.toCryptoDomain(),
                markets = currencies.markets.toMarketDomain(),
                rates = currencies.rates.toRateDomain()
            )
        )
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()


    override fun search(query: String): Flow<List<RateDto>> = flow {
        val plainResponse = httpClient.get(BASE_URL).body<String>()
        val currencies = parseCurrencyRates(plainResponse)

        emit(currencies.rates.toRateDomain())
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()
}