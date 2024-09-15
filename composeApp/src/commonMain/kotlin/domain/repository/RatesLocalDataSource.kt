package domain.repository

import data.local.model.AssetPriceEntity
import data.local.model.main.CurrenciesEntity
import data.local.model.main.detail.CryptoInfoEntity
import domain.model.AssetPriceItem
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import kotlinx.coroutines.flow.Flow

interface RatesLocalDataSource {
    fun getRates(): Flow<Currencies?>
    suspend fun insertRates(rates: CurrenciesEntity)
    suspend fun deleteRates()

    fun getLivePricesFromDb(): Flow<List<AssetPriceItem>>
    suspend fun insertLivePrices(assetPriceEntities: List<AssetPriceEntity>)
    suspend fun deleteAllLivePrices()

    fun getCryptoInfoBySymbol(symbol: String): Flow<CryptoInfo?>
    suspend fun insertCryptoInfo(cryptoInfoEntity: CryptoInfoEntity)
    suspend fun deleteCryptoInfoBySymbol(symbol: String)
}