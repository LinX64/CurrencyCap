package data.repository.main

import domain.model.DataDao
import domain.model.RateDao
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    /**
     * Get rates from the CoinCAP API
     * i.e {"USD": {
     *        "id": "euro",
     *        "symbol": "EUR",
     *        "currencySymbol": "€",
     *        "type": "fiat",
     *        "rateUsd": "1.0865065663024334"
     *     }
     */
    fun getCoinCapRates(): Flow<List<DataDao>>

    /**
     * Get Iranian fiat rates from the Bonbast API
     * i.e: "name": "US Dollar", "sell": 59200, "buy": 59100}
     */
    fun getIranianRate(): Flow<List<RateDao>>
}