package data.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
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
    crossinline shouldFetch: (ResultType?) -> Boolean = { true },
    crossinline isFresh: (ResultType?) -> Boolean = { false },
    crossinline clearLocalData: suspend () -> Unit = { },
    forceRefresh: Boolean = false
): Flow<NetworkResult<ResultType>> = channelFlow {
    send(NetworkResult.Loading(null))

    /**
     * FORCE REFRESH LOGIC:
     * If forceRefresh is true, clear the local data and fetch the data from the network.
     * If the fetch is successful, save the fetched data.
     */
    if (forceRefresh) {
        clearLocalData()

        try {
            val fetchedData = fetch()
            saveFetchResult(fetchedData)
        } catch (e: Exception) {
            onFetchFailed(e)
            send(NetworkResult.Error(e, null))
            return@channelFlow
        }
    }

    /**
     * NORMAL QUERY LOGIC:
     * Collect the local data and check if it is empty or stale.
     * If the data is not empty and fresh, send the data.
     * If the data is empty or stale, fetch the data from the network.
     */
    query().collect { localData ->
        val isEmpty = localData == null || (localData as? Collection<*>)?.isEmpty() ?: false

        if (localData != null && !isEmpty) {
            val freshness = if (isFresh(localData)) DataFreshness.FRESH else DataFreshness.STALE
            send(NetworkResult.Success(localData, freshness))
        }

        if (shouldFetch(localData) && !forceRefresh) {
            try {
                val fetchedData = fetch()
                saveFetchResult(fetchedData)
            } catch (e: Exception) {
                onFetchFailed(e)
                send(NetworkResult.Error(e, localData))
            }
        }
    }
}

enum class DataFreshness {
    FROM_CACHE,
    FRESH,
    STALE
}
