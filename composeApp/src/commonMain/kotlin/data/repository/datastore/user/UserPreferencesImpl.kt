package data.repository.datastore.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {

    private companion object {
        private const val PREFS_TAG_KEY = "UserPreferences"
        private const val IS_USER_LOGGED_IN = "is_user_logged_in"
        private val USER_UID_KEY = stringPreferencesKey("user_uid")
        private val isUserLoggedInKey = booleanPreferencesKey(IS_USER_LOGGED_IN)
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[isUserLoggedInKey] ?: false
        }.first()
    }

    override suspend fun saveUserUid(uid: String) {
        dataStore.edit { preferences ->
            preferences[USER_UID_KEY] = uid
        }
    }

    override suspend fun getUserUid(): String {
        return dataStore.data.map { preferences ->
            preferences[USER_UID_KEY] ?: ""
        }.first()
    }
}