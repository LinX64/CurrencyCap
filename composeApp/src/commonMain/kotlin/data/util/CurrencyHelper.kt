package data.util

import data.remote.model.main.BonbastRateDto
import data.remote.model.main.CryptoDto
import data.remote.model.main.CurrenciesDto
import data.remote.model.main.MarketDto
import data.remote.model.main.RateDto
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal fun parseCurrencyRates(jsonString: String): CurrenciesDto {
    val jsonElement = Json.parseToJsonElement(jsonString).jsonObject

    // Converting bonbast fields
    val bonbast = jsonElement["bonbast"]?.jsonArray ?: throw IllegalStateException("bonbast field is missing")
    val bonbastRates = bonbast.mapNotNull { rateObject ->
        if (rateObject is JsonObject) {
            rateObject.entries.firstOrNull()?.let { (key, value) ->
                val sell = value.jsonObject["sell"]?.jsonPrimitive?.doubleOrNull ?: return@let null
                val buy = value.jsonObject["buy"]?.jsonPrimitive?.doubleOrNull ?: return@let null
                BonbastRateDto(code = key, sell = sell, buy = buy)
            }
        } else throw IllegalStateException("Expected JsonObject but got ${rateObject::class.simpleName}")
    }

    // Extracting and converting other fields
    val crypto = jsonElement["crypto"]?.jsonArray ?: throw IllegalStateException("crypto field is missing")
    val cryptoList = crypto.map { Json.decodeFromJsonElement<CryptoDto>(it) }

    val markets = jsonElement["markets"]?.jsonArray ?: throw IllegalStateException("markets field is missing")
    val marketsList = markets.map { Json.decodeFromJsonElement<MarketDto>(it) }

    val rates = jsonElement["rates"]?.jsonArray ?: throw IllegalStateException("rates field is missing")
    val ratesList = rates.map { Json.decodeFromJsonElement<RateDto>(it) }

    return CurrenciesDto(
        bonbast = bonbastRates.toImmutableList(),
        crypto = cryptoList.toImmutableList(),
        market = marketsList.toImmutableList(),
        rates = ratesList.toImmutableList()
    )
}