package data.remote.repository.profile

import data.remote.model.User
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun saveUserProfile(user: User) {
        val uid = auth.currentUser?.uid.orEmpty()

        firestore.collection(USERS_COLLECTION)
            .document(uid)
            .set(hashMapOf("fullName" to user.fullName, "phoneNumber" to user.phoneNumber))
    }

    private companion object {
        const val USERS_COLLECTION = "users"
    }
}