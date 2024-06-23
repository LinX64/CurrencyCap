package data.repository.main

import data.model.CoinCapRates
import data.model.GetCurrencies
import data.model.toDomain
import data.util.APIConst.BASE_URL
import data.util.APIConst.COINCAP_API
import data.util.retryOnIOException
import domain.model.DataDao
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

    override fun getAllRates(): Flow<GetCurrencies> = flow {
        val rates = httpClient.get(BASE_URL).body<GetCurrencies>()
        emit(rates)
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()


    private suspend fun getCoinCapRate(): List<DataDao> {
        return httpClient.get(COINCAP_API).body<CoinCapRates>().data.toDomain()
    }
}