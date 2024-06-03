package data.repository

import domain.model.RateDao
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getCurrencies(): Flow<List<RateDao>>
}