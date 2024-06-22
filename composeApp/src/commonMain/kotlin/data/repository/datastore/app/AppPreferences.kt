package data.repository.datastore.app

import androidx.datastore.preferences.core.Preferences

interface AppPreferences {
    suspend fun isDarkModeEnabled(): Boolean
    suspend fun changeDarkMode(isEnabled: Boolean): Preferences
}
