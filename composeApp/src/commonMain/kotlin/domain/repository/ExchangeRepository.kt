package domain.repository

import domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getLatest(forceRefresh: Boolean = false): Flow<List<Currency>>
}
