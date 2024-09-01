package domain.repository

import data.remote.model.User
import kotlinx.coroutines.flow.Flow

interface AuthServiceRepository {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String): Result<User>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<User>

    suspend fun sendVerificationCodeToEmail(email: String)

    suspend fun updateCurrentUser(user: User)
    suspend fun updatePhoneNumber(phoneNumber: String)

    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}