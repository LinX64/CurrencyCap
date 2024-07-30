package domain.repository

import data.local.model.main.CurrenciesEntity
import domain.model.main.Currencies
import kotlinx.coroutines.flow.Flow

interface RatesLocalDataSource {
    fun getRates(): Flow<Currencies>
    suspend fun insertRates(rates: CurrenciesEntity)
    suspend fun deleteRates()
}