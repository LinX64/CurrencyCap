package data.local.datastore.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {

    private companion object {
        private const val IS_USER_LOGGED_IN = "is_user_logged_in"
        private val USER_UID_KEY = stringPreferencesKey("user_uid")
        private val isUserLoggedInKey = booleanPreferencesKey(IS_USER_LOGGED_IN)
        private val USER_SELECTED_START_DATE_KEY = stringPreferencesKey("user_selected_start_date")
        private val USER_SELECTED_END_DATE_KEY = stringPreferencesKey("user_selected_end_date")
        private val USER_SELECTED_SOURCES_KEY = stringPreferencesKey("user_selected_sources")
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[isUserLoggedInKey] ?: false
        }.first()
    }

    override suspend fun saveUserUid(uid: String) {
        dataStore.edit { preferences ->
            preferences[USER_UID_KEY] = uid
            preferences[isUserLoggedInKey] = true
        }
    }

    override suspend fun getUserUid(): String {
        return dataStore.data.map { preferences ->
            preferences[USER_UID_KEY] ?: ""
        }.first()
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun saveUserSelectedDates(startDate: String, endDate: String) {
        dataStore.edit { preferences ->
            preferences[USER_SELECTED_START_DATE_KEY] = startDate
            preferences[USER_SELECTED_END_DATE_KEY] = endDate
        }
    }

    override suspend fun getUserSelectedDates(): Pair<String, String> {
        return dataStore.data.map { preferences ->
            Pair(
                preferences[USER_SELECTED_START_DATE_KEY] ?: "",
                preferences[USER_SELECTED_END_DATE_KEY] ?: ""
            )
        }.first()
    }

    override suspend fun saveUserSelectedSources(sources: Set<String>) {
        dataStore.edit { preferences ->
            preferences[USER_SELECTED_SOURCES_KEY] = sources.joinToString(",")
        }
    }

    override suspend fun getUserSelectedSources(): Set<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_SELECTED_SOURCES_KEY]?.split(",")?.toSet() ?: emptySet()
        }.first()
    }
}