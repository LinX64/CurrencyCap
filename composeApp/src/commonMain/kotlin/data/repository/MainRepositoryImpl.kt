package data.repository

import data.model.CoinCapRates
import data.model.toDomain
import data.util.APIConst.BASE_URL
import data.util.APIConst.COINCAP_API
import data.util.parseCurrencyRates
import domain.model.DataDao
import domain.model.RateDao
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
        val response = httpClient.get(COINCAP_API).body<CoinCapRates>().data.toDomain()
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getIranianRate(): Flow<List<RateDao>> = flow {
        val response = httpClient.get(BASE_URL).body<String>()
        val rates = parseCurrencyRates(response).toDomain()
        emit(rates)
    }.flowOn(Dispatchers.IO)
}