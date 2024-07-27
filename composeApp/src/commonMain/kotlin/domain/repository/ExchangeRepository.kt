package domain.repository

import domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getLatest(): Flow<List<Currency>>
}
