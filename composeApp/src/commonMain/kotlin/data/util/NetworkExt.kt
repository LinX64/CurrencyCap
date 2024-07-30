package data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import okio.IOException
import kotlin.math.pow
import kotlin.random.Random

fun <T> Flow<T>.retryOnIOException(
    maxRetries: Int = 60,
    initialRetryDelay: Long = 2000L,
    maxRetryDelay: Long = 30000L
): Flow<T> = retryWhen { cause, attempt ->
    if (cause is IOException && attempt < maxRetries) {
        val exponentialDelay = initialRetryDelay * 2.0.pow(attempt.toDouble()).toLong()
        val retryDelay =
            (exponentialDelay + Random.nextLong(-exponentialDelay / 2, exponentialDelay / 2)).coerceAtMost(maxRetryDelay)
        delay(retryDelay)
        true
    } else false
} // TODO: Consider adding internet connection check

internal inline fun <ResultType, RequestType> cacheDataOrFetchOnline(
    crossinline query: () -> Flow<ResultType?>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType?) -> Boolean = { true }
): Flow<NetworkResult<ResultType>> = channelFlow {
    send(NetworkResult.Loading(null))

    val queryFlow = query()
    queryFlow.collect { localData ->
        if (localData != null) {
            send(NetworkResult.Success(localData))
        }

        if (shouldFetch(localData)) {
            try {
                val fetchedData = fetch()
                saveFetchResult(fetchedData)
                // The updated data will be emitted in the next iteration of this collect
            } catch (e: Exception) {
                onFetchFailed(e)
                send(NetworkResult.Error(e, localData))
            }
        }
    }
}
    .flowOn(Dispatchers.IO)
    .retryOnIOException()