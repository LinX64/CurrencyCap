package domain.repository

interface UserPreferences {
    suspend fun isUserLoggedIn(): Boolean
    suspend fun saveUserUid(uid: String)
    suspend fun getUserUid(): String
    suspend fun clear()

    suspend fun saveUserSelectedDates(startDate: String, endDate: String)
    suspend fun getUserSelectedDates(): Pair<String, String>
}
