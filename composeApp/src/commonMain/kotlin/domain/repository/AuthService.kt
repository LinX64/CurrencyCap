package domain.repository

import data.model.User
import data.repository.auth.AuthServiceImpl.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String): AuthState
    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthState

    suspend fun updateCurrentUser(user: User)

    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}