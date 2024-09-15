package data.local.database

import data.local.model.AssetPriceEntity
import data.local.model.main.CurrenciesEntity
import data.local.model.main.detail.CryptoInfoEntity
import data.local.model.main.detail.toDomain
import data.local.model.main.toDomain
import data.local.model.toDomain
import domain.model.AssetPriceItem
import domain.model.main.CryptoInfo
import domain.model.main.Currencies
import domain.repository.RatesLocalDataSource
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RatesLocalDataSourceImpl(
    private val realm: Realm
) : RatesLocalDataSource {

    override fun getRates(): Flow<Currencies?> = realm.query<CurrenciesEntity>()
        .first()
        .asFlow()
        .map { realmResult -> realmResult.obj?.toDomain() }

    override suspend fun insertRates(rates: CurrenciesEntity) {
        realm.write {
            copyToRealm(rates, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun deleteRates() {
        realm.write {
            val rates = query<CurrenciesEntity>().first()
            delete(rates)
        }
    }

    override fun getLivePricesFromDb(): Flow<List<AssetPriceItem>> {
        return realm.query<AssetPriceEntity>().asFlow()
            .map { realmResults ->
                realmResults.list.map { it.toDomain() }
            }
    }

    override suspend fun insertLivePrices(assetPriceEntities: List<AssetPriceEntity>) {
        realm.writeBlocking {
            assetPriceEntities.forEach { copyToRealm(it, updatePolicy = UpdatePolicy.ALL) }
        }
    }

    override suspend fun deleteAllLivePrices() {
        realm.write {
            val assetPrice = query<AssetPriceEntity>().first()
            delete(assetPrice)
        }
    }

    override fun getCryptoInfoBySymbol(symbol: String): Flow<CryptoInfo?> {
        return realm.query<CryptoInfoEntity>("symbol == $0", symbol)
            .first()
            .asFlow()
            .map { realmResult -> realmResult.obj }
            .map { it?.toDomain() }
    }

    override suspend fun insertCryptoInfo(cryptoInfoEntity: CryptoInfoEntity) {
        realm.writeBlocking {
            copyToRealm(cryptoInfoEntity, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun deleteCryptoInfoBySymbol(symbol: String) {
        realm.write {
            val cryptoInfo = query<CryptoInfoEntity>("symbol == $0", symbol).first()
            delete(cryptoInfo)
        }
    }
}
