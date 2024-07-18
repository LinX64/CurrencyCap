package domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    suspend fun isUserLoggedIn(): Boolean
    suspend fun saveUserUid(uid: String)
    suspend fun getUserUid(): String
    suspend fun clear()

    suspend fun saveUserSelectedDates(startDate: String, endDate: String)
    suspend fun getUserSelectedDates(): Pair<String, String>

    suspend fun saveUserSelectedSources(sources: Set<String>)
    suspend fun getUserSelectedSources(): Set<String>

    suspend fun setDarkMode(isDarkMode: Boolean)
    fun isDarkMode(): Flow<Boolean>

    suspend fun setPushNotificationEnabled(isPushNotificationEnabled: Boolean)
    suspend fun isPushNotificationEnabled(): Boolean
}
