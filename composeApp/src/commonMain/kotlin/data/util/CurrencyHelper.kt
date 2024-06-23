package data.util

import data.model.BonbastRate
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun parseCurrencyRates(jsonString: String): List<BonbastRate> {
    val jsonElement = Json.parseToJsonElement(jsonString).jsonObject
    return jsonElement.map { (key, value) ->
        val sell = value.jsonObject["sell"]?.jsonPrimitive?.intOrNull ?: 0
        val buy = value.jsonObject["buy"]?.jsonPrimitive?.intOrNull ?: 0
        BonbastRate(code = key, sell = sell, buy = buy)
    }
}