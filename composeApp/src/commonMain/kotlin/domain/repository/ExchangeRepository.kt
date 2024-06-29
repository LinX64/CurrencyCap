package domain.repository

import data.local.model.exchange.Currency
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getLatest(): Flow<List<Currency>>
}
