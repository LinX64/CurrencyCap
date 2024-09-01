package data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext

sealed class NetworkError : Exception() {
    data object NoInternet : NetworkError()
    data object Timeout : NetworkError()
    data class Unknown(override val message: String?) : NetworkError()
}

class NetworkErrorHandler(private val dispatcher: CoroutineDispatcher = Dispatchers.Default) {

    suspend fun <T> handleNetworkCall(call: suspend () -> T): Result<T> {
        return withContext(dispatcher) {
            try {
                Result.success(call())
            } catch (e: Exception) {
                Result.failure(mapToNetworkError(e))
            }
        }
    }

    private fun mapToNetworkError(exception: Exception): NetworkError {
        return when (exception) {
            is TimeoutCancellationException -> NetworkError.Timeout
            else -> when {
                exception.message?.contains(
                    "Unable to resolve host",
                    ignoreCase = true
                ) == true -> NetworkError.NoInternet

                exception.message?.contains(
                    "timeout",
                    ignoreCase = true
                ) == true -> NetworkError.Timeout

                exception.message?.contains(
                    "connection",
                    ignoreCase = true
                ) == true -> NetworkError.NoInternet

                else -> NetworkError.Unknown(exception.message)
            }
        }
    }
}