package data.repository.auth

import data.model.User
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthServiceImpl(
    private val auth: FirebaseAuth,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : AuthService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User> = auth.authStateChanged.map { user ->
        user?.let { User(it.uid, it.isAnonymous) } ?: User()
    }

    override suspend fun authenticate(
        email: String,
        password: String
    ): AuthResponse {
        return try {
            AuthResponse.Loading
            val response = scope.async { auth.signInWithEmailAndPassword(email, password) }.await()
            AuthResponse.Success(response.user?.uid ?: response.user?.email.orEmpty())
        } catch (e: Exception) {
            AuthResponse.Error(e.message ?: "User not found")
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResponse {
        return try {
            AuthResponse.Loading
            val response = scope.async { auth.createUserWithEmailAndPassword(email, password) }.await()
            AuthResponse.Success(response.user?.uid ?: response.user?.email.orEmpty())
        } catch (e: Exception) {
            AuthResponse.Error(e.message ?: "Could not create user!")
        }
    }

    override suspend fun sendRecoveryEmail(email: String) = launchWithAwait { auth.sendPasswordResetEmail(email) }

    override suspend fun deleteAccount() = launchWithAwait { auth.currentUser!!.delete() }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()
    }

    private suspend fun launchWithAwait(block: suspend () -> Unit) {
        scope.async { block() }.await()
    }
}