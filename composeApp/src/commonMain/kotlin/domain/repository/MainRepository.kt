package domain.repository

import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import org.mobilenativefoundation.store.store5.Store

interface MainRepository {
    fun getAllRatesNew(): Store<String, Currencies>
    fun getCryptoInfoBySymbolNew(
        id: String,
        symbol: String
    ): Store<Any, CryptoInfo>
}