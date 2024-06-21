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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl(
    private val httpClient: HttpClient
) : MainRepository {

    override fun getCoinCapRates(): Flow<List<DataDao>> = flow {
        emit(getCoinCapRate())
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    override fun getIranianRate(): Flow<List<RateDao>> = flow {
        val response = httpClient.get(BASE_URL).body<String>()
        val rates = parseCurrencyRates(response).toDomain()
        emit(rates)
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    override fun search(query: String): Flow<List<DataDao>> = flow {
        val rates = getCoinCapRate()
        val filteredRates = rates.filter {
            it.symbol.contains(query, ignoreCase = true)
        }
        emit(filteredRates)
    }
        .flowOn(Dispatchers.IO)

    private suspend fun getCoinCapRate(): List<DataDao> {
        return httpClient.get(COINCAP_API).body<CoinCapRates>().data.toDomain()
    }
}