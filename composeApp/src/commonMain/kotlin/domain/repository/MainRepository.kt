package domain.repository

import domain.model.CurrenciesDto
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllRates(): Flow<CurrenciesDto>
    //fun search(query: String): Flow<List<DataDao>>
}