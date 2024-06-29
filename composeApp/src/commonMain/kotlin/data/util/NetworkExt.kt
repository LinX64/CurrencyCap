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

inline fun <ResultType, RequestType> offlineFirst(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResultToDB: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType?) -> Boolean = { true }
): Flow<NetworkResult<ResultType>> = channelFlow {
    send(NetworkResult.Loading())

    try {
        val initialData = query().first()

        if (shouldFetch(initialData)) {
            val request = fetch()
            saveFetchResultToDB(request)
            query().collect { send(NetworkResult.Success(it)) }
        } else send(NetworkResult.Success(initialData))

    } catch (e: Exception) {
        onFetchFailed(e)
        query().collect { send(NetworkResult.Error(e, it)) }
    }
}
    .flowOn(Dispatchers.IO)
    .retryOnIOException()