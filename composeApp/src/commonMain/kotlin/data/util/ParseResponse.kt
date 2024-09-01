package data.util

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

suspend inline fun <reified T> parseResponse(response: HttpResponse): T {
    val json = Json { ignoreUnknownKeys = true }
    val jsonString = response.bodyAsText()
    return json.decodeFromString<T>(jsonString)
}

suspend inline fun <reified T> parseListResponse(response: HttpResponse): List<T> {
    val json = Json { ignoreUnknownKeys = true }
    val jsonString = response.bodyAsText()
    return json.decodeFromString<List<T>>(jsonString)
}
