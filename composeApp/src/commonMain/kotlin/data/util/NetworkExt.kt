package data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import okio.IOException

fun <T> Flow<T>.retryOnIOException(
    maxRetries: Int = 60,
    retryDelay: Long = 2000L
): Flow<T> = retryWhen { cause, attempt ->
    if (cause is IOException && attempt < maxRetries) {
        delay(retryDelay)
        return@retryWhen true
    } else {
        return@retryWhen false
    }
}

inline fun <ResultType, RequestType> networkBoundNetworkResult(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<NetworkResult<ResultType>> = channelFlow {
    try {
        send(NetworkResult.Loading())
        val data = query().first()

        if (shouldFetch(data)) {
            val request = fetch()

            saveFetchResult(request)
            query().collect { send(NetworkResult.Success(it)) }
        } else {
            query().collect { send(NetworkResult.Success(it)) }
        }
    } catch (e: Exception) {
        onFetchFailed(e)
        query().collect { send(NetworkResult.Error(e, it)) }
    }
}
    .flowOn(Dispatchers.IO)
    .retryOnIOException()