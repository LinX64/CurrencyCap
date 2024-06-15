package data.repository.main

import data.model.CoinCapRates
import data.model.toDomain
import data.util.APIConst.BASE_URL
import data.util.APIConst.COINCAP_API
import data.util.parseCurrencyRates
import data.util.retryOnIOException
import domain.model.DataDao
import domain.model.RateDao
import domain.repository.MainRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MainRepository {

    override fun getCoinCapRates(): Flow<List<DataDao>> = flow {
        val response = httpClient.get(COINCAP_API).body<CoinCapRates>().data.toDomain()
        emit(response)
    }
        .flowOn(ioDispatcher)
        .retryOnIOException()

    override fun getIranianRate(): Flow<List<RateDao>> = flow {
        val response = httpClient.get(BASE_URL).body<String>()
        val rates = parseCurrencyRates(response).toDomain()
        emit(rates)
    }
        .flowOn(ioDispatcher)
        .retryOnIOException()
}