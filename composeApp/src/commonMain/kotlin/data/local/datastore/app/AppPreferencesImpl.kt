package data.local.datastore.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferences {

    private companion object {
        private const val PREFS_TAG_KEY = "AppPreferences"
        private const val IS_DARK_MODE_ENABLED = "prefsBoolean"
        private val LAST_FETCH_TIME = longPreferencesKey("last_fetch_time")
    }

    private val darkModeKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_DARK_MODE_ENABLED")

    override suspend fun isDarkModeEnabled() = dataStore.data.map { preferences ->
        preferences[darkModeKey] ?: false
    }.first()

    override suspend fun changeDarkMode(isEnabled: Boolean) = dataStore.edit { preferences ->
        preferences[darkModeKey] = isEnabled
    }

    override suspend fun getLastFetchTime() = dataStore.data.map { preferences ->
        preferences[LAST_FETCH_TIME] ?: 0
    }.first()

    override suspend fun saveLastFetchTime(time: Long) = dataStore.edit { preferences ->
        preferences[LAST_FETCH_TIME] = time
    }
}