package data.repository.auth

import data.model.User
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String): AuthResponse
    suspend fun signUpWithEmail(email: String, password: String): AuthResponse

    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}