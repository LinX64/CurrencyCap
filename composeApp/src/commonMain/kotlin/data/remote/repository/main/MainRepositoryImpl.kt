package data.remote.repository.main

import data.remote.model.main.toBonbastRateDomain
import data.remote.model.main.toCryptoDomain
import data.remote.model.main.toMarketDomain
import data.remote.model.main.toRateDomain
import data.remote.model.news.ArticleDto
import data.util.APIConst.BASE_URL
import data.util.APIConst.NEWS_URL
import data.util.parseCurrencyRates
import data.util.retryOnIOException
import domain.model.main.Currencies
import domain.model.main.Rate
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

    override fun getAllRates(): Flow<Currencies> = flow {
        val plainResponse = httpClient.get(BASE_URL).body<String>()
        val currencies = parseCurrencyRates(plainResponse)

        emit(
            Currencies(
                bonbast = currencies.bonbast.toBonbastRateDomain(),
                crypto = currencies.crypto.toCryptoDomain(),
                markets = currencies.market.toMarketDomain(),
                rates = currencies.rates.toRateDomain()
            )
        )
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    override fun search(query: String): Flow<List<Rate>> = flow {
        val plainResponse = httpClient.get(BASE_URL).body<String>()
        val currencies = parseCurrencyRates(plainResponse)

        emit(currencies.rates.toRateDomain())
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    override fun getNews(): Flow<List<ArticleDto>> = flow {
        val response = httpClient.get(NEWS_URL).body<List<ArticleDto>>()
        emit(response)
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()
}