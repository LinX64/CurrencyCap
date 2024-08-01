package domain.repository

import data.local.model.main.CurrenciesEntity
import data.local.model.main.detail.CryptoInfoEntity
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import kotlinx.coroutines.flow.Flow

interface RatesLocalDataSource {
    fun getRates(): Flow<Currencies?>
    suspend fun insertRates(rates: CurrenciesEntity)
    suspend fun deleteRates()

    fun getCryptoInfoBySymbol(symbol: String): Flow<CryptoInfo?>
    suspend fun insertCryptoInfo(cryptoInfoEntity: CryptoInfoEntity)
    suspend fun deleteCryptoInfoBySymbol(symbol: String)
}