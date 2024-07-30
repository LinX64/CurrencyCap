package data.local.database

import data.local.model.main.CurrenciesEntity
import data.local.model.main.toDomain
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
}
