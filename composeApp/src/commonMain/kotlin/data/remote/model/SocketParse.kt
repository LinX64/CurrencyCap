package data.remote.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun convertJsonToAssetPrices(jsonString: String): List<AssetPriceItemDto> {
    val jsonObject = Json.decodeFromString<JsonObject>(jsonString)
    return jsonObject.entries.map { (symbol, priceElement) ->
        AssetPriceItemDto(
            symbol = symbol,
            price = (priceElement as JsonPrimitive).content,
            previousPrice = null // We don't have previous price in this data
        )
    }
}