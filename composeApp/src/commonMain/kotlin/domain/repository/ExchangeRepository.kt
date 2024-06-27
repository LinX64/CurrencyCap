package domain.repository

import kotlinx.coroutines.flow.Flow
import ui.screens.exchange.Currency

interface ExchangeRepository {
    fun getLatest(): Flow<List<Currency>>
}
