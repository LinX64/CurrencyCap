package domain.repository

import data.remote.model.exchange.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun saveSourceCurrencyCode(code: String)
    suspend fun saveTargetCurrencyCode(code: String)

    fun readSourceCurrencyCode(): Flow<CurrencyCode>
    fun readTargetCurrencyCode(): Flow<CurrencyCode>
}