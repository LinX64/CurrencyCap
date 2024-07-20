package data.remote.repository.profile

import data.remote.model.User
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.repository.AuthServiceRepository
import domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val authRepository: AuthServiceRepository,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun saveUserProfile(user: User) {
        val uid = authRepository.currentUserId

        firestore.collection(USERS_COLLECTION)
            .document(uid)
            .set(hashMapOf(FULL_NAME_FIELD to user.fullName, PHONE_NUMBER_FIELD to user.phoneNumber))
    }

    override suspend fun getUserFullName(): String {
        val uid = authRepository.currentUserId

        return try {
            val userDocument = firestore.collection(USERS_COLLECTION)
                .document(uid)
                .get()

            userDocument.get(FULL_NAME_FIELD) as? String ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    override suspend fun getUserPhoneNumber(): String {
        val uid = authRepository.currentUserId

        return try {
            val userDocument = firestore.collection(USERS_COLLECTION)
                .document(uid)
                .get()

            userDocument.get(PHONE_NUMBER_FIELD) as? String ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    private companion object {
        const val USERS_COLLECTION = "users"
        const val FULL_NAME_FIELD = "fullName"
        const val PHONE_NUMBER_FIELD = "phoneNumber"
    }
}