package domain.repository

import data.util.NetworkResult
import domain.model.main.Crypto
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllRates(forceRefresh: Boolean = false): Flow<NetworkResult<Currencies>>

    fun getCryptoBySymbol(symbol: String): Flow<Crypto>
    fun getCryptoInfoById(symbol: String): Flow<CryptoInfo>
    suspend fun getCryptoNameBySymbol(symbol: String): String
}