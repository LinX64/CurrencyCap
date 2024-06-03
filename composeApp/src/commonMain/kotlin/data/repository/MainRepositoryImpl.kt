package data.repository

import data.model.Currencies
import data.model.toDomain
import domain.model.RateDao
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val BASE_URL =
    "https://raw.githubusercontent.com/LinX64/CoinCap/f4a2e9922b441eea56d6d4256bfce21de5796675/currencies.json"

class MainRepositoryImpl(
    private val httpClient: HttpClient
) : MainRepository {
    override fun getCurrencies(): Flow<List<RateDao>> = flow {
        val response = httpClient.get(BASE_URL).body<Currencies>().rates.toDomain()
        emit(response)
    }.flowOn(Dispatchers.IO)
}