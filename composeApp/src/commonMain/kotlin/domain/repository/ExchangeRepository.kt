package domain.repository

import domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getLatest(forceRefresh: Boolean = false): Flow<List<CurrencyRate>>
}
