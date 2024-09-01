package data.remote.repository.auth

import data.remote.model.User
import dev.gitlive.firebase.auth.ActionCodeSettings
import dev.gitlive.firebase.auth.AndroidPackageName
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.PhoneAuthCredential
import dev.gitlive.firebase.auth.PhoneAuthProvider
import domain.repository.AuthServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthServiceRepositoryImpl(
    private val auth: FirebaseAuth,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : AuthServiceRepository {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User> = auth.authStateChanged.map { user ->
        user?.let { User(id = it.uid, isAnonymous = it.isAnonymous, email = user.email) } ?: User()
    }

    override suspend fun authenticate(
        email: String,
        password: String
    ): Result<User> {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).user
            val uid = user?.uid
            if (uid?.isNotEmpty() == true) {
                Result.success(User(uid, user.isAnonymous))
            } else Result.failure(Exception("Could not authenticate user!"))
        } catch (e: Exception) {
            Result.failure(Exception(e.message))
        }
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).user
            Result.success(User(user!!.uid, user.isAnonymous))
        } catch (e: Exception) {
            return Result.failure(Exception(e.message))
        }
    }

    override suspend fun sendVerificationCodeToEmail(email: String) {
        try {
            val actionCodeSettings = ActionCodeSettings(
                url = "https://example.com/emailSignIn",
                androidPackageName = AndroidPackageName(
                    "com.client.currencycap",
                    minimumVersion = "12"
                ),
                iOSBundleId = "com.client.currencycap.CurrencyCap",
            )

            auth.sendSignInLinkToEmail(email = email, actionCodeSettings = actionCodeSettings)
        } catch (e: Exception) {
            println("Error sending verification code: ${e.message}")
        }
    }

    override suspend fun updateCurrentUser(user: User) {
        launchWithAwait {
            try {
                auth.currentUser?.let { user ->
                    user.verifyBeforeUpdateEmail(user.email.toString())
                    updatePhoneNumber(user.phoneNumber.toString())
                    println("Profile updated successfully")
                }
            } catch (e: Exception) {
                println("Error updating profile: ${e.message}")
            }
        }
    }

    override suspend fun updatePhoneNumber(phoneNumber: String) {
        val credential = createPhoneAuthCredential(phoneNumber, "")
        auth.currentUser?.updatePhoneNumber(credential = credential)
    }

    override suspend fun sendRecoveryEmail(email: String) = launchWithAwait { auth.sendPasswordResetEmail(email) }

    override suspend fun deleteAccount() = launchWithAwait {
        try {
            auth.currentUser!!.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signOut() {
        if (auth.currentUser?.isAnonymous == true) {
            auth.currentUser?.delete()
        }
        auth.signOut()
    }

    private suspend fun launchWithAwait(block: suspend () -> Unit) {
        scope.async { block() }.await()
    }

    private fun createPhoneAuthCredential(
        verificationId: String,
        verificationCode: String
    ): PhoneAuthCredential {
        return PhoneAuthProvider().credential(verificationId, verificationCode)
    }
}
