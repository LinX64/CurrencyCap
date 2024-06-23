package domain.repository

import data.model.GetCurrencies
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllRates(): Flow<GetCurrencies>
    //fun search(query: String): Flow<List<DataDao>>
}