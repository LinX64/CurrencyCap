package data.repository.auth

sealed class AuthResponse {
    data object Loading : AuthResponse()
    data class Success(val uid: String) : AuthResponse()
    data class Error(val message: String) : AuthResponse()
}