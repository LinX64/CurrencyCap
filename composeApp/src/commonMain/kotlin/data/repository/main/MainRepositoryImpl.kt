package data.repository.main

import data.model.Currencies
import data.model.toDomain
import data.util.APIConst.BASE_URL
import data.util.retryOnIOException
import domain.model.CurrenciesDto
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
        val rates = httpClient.get(BASE_URL).body<Currencies>().toDomain()
        emit(rates)
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()
}