package data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T, val freshness: DataFreshness) : NetworkResult<T>()
    data class Error<out T>(val throwable: Throwable, val data: T? = null) : NetworkResult<T>()
    data class Loading<out T>(val data: T? = null) : NetworkResult<T>()
}

fun <T> Flow<T>.asResult(): Flow<NetworkResult<T>> =
    map<T, NetworkResult<T>> { NetworkResult.Success(it, DataFreshness.FRESH) }
        .onStart { emit(NetworkResult.Loading(null)) }
        .catch { emit(NetworkResult.Error(it)) }

sealed class DataFreshness {
    data object FRESH : DataFreshness()
    data object STALE : DataFreshness()
}