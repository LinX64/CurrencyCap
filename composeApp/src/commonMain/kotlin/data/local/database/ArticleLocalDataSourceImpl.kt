package data.local.database

import data.local.model.ArticleEntity
import domain.repository.ArticleLocalDataSource
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleLocalDataSourceImpl(
    private val realm: Realm
) : ArticleLocalDataSource {

    override suspend fun insertCurrencyData(currency: ArticleEntity) {
        realm.write {
            copyToRealm(currency)
        }
    }

    override fun readCurrencyData(): Flow<List<ArticleEntity>> {
        return realm.query<ArticleEntity>()
            .asFlow()
            .map { result ->
                result.list
            }
    }

    override suspend fun cleanUp() {
        return realm.write {
            val currencyCollection = this.query<ArticleEntity>()
            delete(currencyCollection)
        }
    }
}