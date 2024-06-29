package domain.repository

import data.remote.model.exchange.Currency
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getLatest(): Flow<List<Currency>>
}
