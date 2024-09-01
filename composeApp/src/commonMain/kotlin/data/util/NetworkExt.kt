package data.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
            (exponentialDelay + Random.nextLong(
                -exponentialDelay / 2,
                exponentialDelay / 2
            )).coerceAtMost(maxRetryDelay)
        delay(retryDelay)
        true
    } else false
} // TODO: Consider adding internet connection check

