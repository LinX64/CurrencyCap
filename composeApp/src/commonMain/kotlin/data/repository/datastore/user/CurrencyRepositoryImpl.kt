import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import data.model.exchange.CurrencyCode
import data.util.Constant
import domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : CurrencyRepository {

    override suspend fun saveSourceCurrencyCode(code: String) {
        dataStore.edit { preferences ->
            preferences[Constant.KEY_SOURCE_CURRENCY] = code
        }
    }

    override suspend fun saveTargetCurrencyCode(code: String) {
        dataStore.edit { preferences ->
            preferences[Constant.KEY_TARGET_CURRENCY] = code
        }
    }

    override fun readSourceCurrencyCode(): Flow<CurrencyCode> {
        return dataStore.data
            .map { preferences ->
                CurrencyCode.valueOf(preferences[Constant.KEY_SOURCE_CURRENCY] ?: Constant.DEFAULT_SOURCE_CURRENCY)
            }
    }

    override fun readTargetCurrencyCode(): Flow<CurrencyCode> {
        return dataStore.data
            .map { preferences ->
                CurrencyCode.valueOf(preferences[Constant.KEY_TARGET_CURRENCY] ?: Constant.DEFAULT_TARGET_CURRENCY)
            }
    }
}