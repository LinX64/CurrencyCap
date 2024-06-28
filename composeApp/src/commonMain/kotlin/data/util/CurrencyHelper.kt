package data.util

import data.model.main.BonbastRate
import data.model.main.Crypto
import data.model.main.Currencies
import data.model.main.Market
import data.model.main.Rate
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal fun parseCurrencyRates(jsonString: String): Currencies {
    val jsonElement = Json.parseToJsonElement(jsonString).jsonObject

    // Converting bonbast fields
    val bonbast = jsonElement["bonbast"]?.jsonArray ?: throw IllegalStateException("bonbast field is missing")
    val bonbastRates = bonbast.mapNotNull { rateObject ->
        if (rateObject is JsonObject) {
            rateObject.entries.firstOrNull()?.let { (key, value) ->
                val sell = value.jsonObject["sell"]?.jsonPrimitive?.doubleOrNull ?: return@let null
                val buy = value.jsonObject["buy"]?.jsonPrimitive?.doubleOrNull ?: return@let null
                BonbastRate(code = key, sell = sell, buy = buy)
            }
        } else throw IllegalStateException("Expected JsonObject but got ${rateObject::class.simpleName}")
    }

    // Extracting and converting other fields
    val crypto = jsonElement["crypto"]?.jsonArray ?: throw IllegalStateException("crypto field is missing")
    val cryptoList = crypto.map { Json.decodeFromJsonElement<Crypto>(it) }

    val markets = jsonElement["markets"]?.jsonArray ?: throw IllegalStateException("markets field is missing")
    val marketsList = markets.map { Json.decodeFromJsonElement<Market>(it) }

    val rates = jsonElement["rates"]?.jsonArray ?: throw IllegalStateException("rates field is missing")
    val ratesList = rates.map { Json.decodeFromJsonElement<Rate>(it) }

    return Currencies(
        bonbast = bonbastRates,
        crypto = cryptoList,
        markets = marketsList,
        rates = ratesList
    )
}