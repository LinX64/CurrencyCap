package data.local.database

import data.local.model.main.CurrenciesEntity
import data.local.model.main.toDomain
import domain.model.main.Currencies
import domain.repository.RatesLocalDataSource
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class RatesLocalDataSourceImpl(
    private val realm: Realm
) : RatesLocalDataSource {

    override fun getRates(): Flow<Currencies> {
        return realm.query<CurrenciesEntity>()
            .first()
            .asFlow()
            .map { it.obj }
            .filterNotNull()
            .map { it.toDomain() }
    }

    override suspend fun insertRates(rates: CurrenciesEntity) {
        realm.write {
            copyToRealm(rates)
        }
    }

    override suspend fun deleteRates() {
        realm.write {
            val rates = query<CurrenciesEntity>().first()
            delete(rates)
        }
    }
}
