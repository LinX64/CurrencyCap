package domain.repository

import data.remote.model.User

interface ProfileRepository {
    suspend fun saveUserProfile(user: User)
    suspend fun getUserFullName(): String
    suspend fun getUserPhoneNumber(): String
}