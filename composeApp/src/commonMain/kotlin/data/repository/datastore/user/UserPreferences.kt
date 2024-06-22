package data.repository.datastore.user

interface UserPreferences {
    suspend fun isUserLoggedIn(): Boolean
    suspend fun saveUserUid(uid: String)
    suspend fun getUserUid(): String
}
