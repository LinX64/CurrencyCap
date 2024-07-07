import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import data.local.model.exchange.CurrencyCode
import domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : CurrencyRepository {

    override suspend fun saveSourceCurrencyCode(code: String) {
        dataStore.edit { preferences ->
            preferences[KEY_SOURCE_CURRENCY] = code
        }
    }

    override suspend fun saveTargetCurrencyCode(code: String) {
        dataStore.edit { preferences ->
            preferences[KEY_TARGET_CURRENCY] = code
        }
    }

    override fun readSourceCurrencyCode(): Flow<CurrencyCode> {
        return dataStore.data
            .map { preferences ->
                CurrencyCode.valueOf(preferences[KEY_SOURCE_CURRENCY] ?: DEFAULT_SOURCE_CURRENCY)
            }
    }

    override fun readTargetCurrencyCode(): Flow<CurrencyCode> {
        return dataStore.data
            .map { preferences ->
                CurrencyCode.valueOf(preferences[KEY_TARGET_CURRENCY] ?: DEFAULT_TARGET_CURRENCY)
            }
    }

    private companion object {
        val KEY_SOURCE_CURRENCY = stringPreferencesKey("sourceCurrency")
        val KEY_TARGET_CURRENCY = stringPreferencesKey("targetCurrency")
        val DEFAULT_SOURCE_CURRENCY = CurrencyCode.USD.name
        val DEFAULT_TARGET_CURRENCY = CurrencyCode.EUR.name
    }
}