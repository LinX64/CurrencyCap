package data.local.datastore.app

import androidx.datastore.preferences.core.Preferences

interface AppPreferences {
    suspend fun isDarkModeEnabled(): Boolean
    suspend fun changeDarkMode(isEnabled: Boolean): Preferences

    suspend fun getLastFetchTime(): Long
    suspend fun saveLastFetchTime(time: Long): Preferences
}
