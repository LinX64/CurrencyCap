package domain.repository

import data.model.User
import dev.gitlive.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String): AuthResult
    suspend fun signUpWithEmail(email: String, password: String): AuthResult
    suspend fun signUpWithEmailOnly(email: String)

    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}