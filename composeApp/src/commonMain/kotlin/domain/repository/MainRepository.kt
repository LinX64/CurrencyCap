package domain.repository

import data.util.NetworkResult
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getAllRates(forceRefresh: Boolean = false): Flow<NetworkResult<Currencies>>
    fun getCryptoInfoBySymbol(
        forceRefresh: Boolean = false,
        symbol: String
    ): Flow<NetworkResult<CryptoInfo>>
}