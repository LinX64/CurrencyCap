package di

import data.util.APIConst.BASE_COIN_GECKO_URL
import data.util.APIConst.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val httpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        coerceInputValues = true
                    },
                    contentType = ContentType.Application.Json
                )
            }
            install(Logging) {
                level = LogLevel.INFO
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
            }
            install(Resources)

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}

fun HttpRequestBuilder.baseApi() {
    url {
        protocol = URLProtocol.HTTPS
        host = BASE_URL
    }
}

fun HttpRequestBuilder.coinGeckoApi() {
    url {
        protocol = URLProtocol.HTTPS
        host = BASE_COIN_GECKO_URL
    }
}

