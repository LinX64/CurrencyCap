package data.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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