package domain.repository

import data.remote.model.User

interface ProfileRepository {
    suspend fun saveUserProfile(user: User)
}