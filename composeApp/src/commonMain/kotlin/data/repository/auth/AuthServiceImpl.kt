package data.repository.auth

import data.model.User
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.PhoneAuthCredential
import dev.gitlive.firebase.auth.PhoneAuthProvider
import domain.repository.AuthService
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
    ): AuthState = try {
        val user = auth.signInWithEmailAndPassword(email, password).user
        AuthState.Success(User(user!!.uid, user.isAnonymous))
    } catch (e: Exception) {
        AuthState.Error(e.message ?: "Could not authenticate user!")
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthState = try {
        val user = auth.createUserWithEmailAndPassword(email, password).user
        AuthState.Success(User(user!!.uid, user.isAnonymous))
    } catch (e: Exception) {
        AuthState.Error(e.message ?: "Could not create user!")
    }

    override suspend fun updateCurrentUser(user: User) {
        auth.currentUser?.updateProfile(displayName = user.name)
    }

    override suspend fun updatePhoneNumber(phoneNumber: String) {
        val credential = createPhoneAuthCredential(phoneNumber, "")
        auth.currentUser?.updatePhoneNumber(credential = credential)
    }

    override suspend fun sendRecoveryEmail(email: String) = launchWithAwait { auth.sendPasswordResetEmail(email) }

    override suspend fun deleteAccount() = launchWithAwait { auth.currentUser!!.delete() }

    override suspend fun signOut() {
        if (auth.currentUser?.isAnonymous == true) {
            auth.currentUser?.delete()
        }
        auth.signOut()
    }

    private suspend fun launchWithAwait(block: suspend () -> Unit) {
        scope.async { block() }.await()
    }

    private fun createPhoneAuthCredential(verificationId: String, verificationCode: String): PhoneAuthCredential {
        return PhoneAuthProvider().credential(verificationId, verificationCode)
    }

    sealed class AuthState {
        data class Success(val user: User) : AuthState()
        data class Error(val message: String) : AuthState()
    }
}