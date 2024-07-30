package data.remote.model.main

import data.local.model.main.RateEntity
import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RateDto(
    @SerialName("id")
    val id: String,
    @SerialName("rateUsd")
    val rateUsd: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("type")
    val type: String,
    @SerialName("currencySymbol")
    val currencySymbol: String? = null
) {
    fun toEntity() = RateEntity().apply {
        id = this@RateDto.id
        rateUsd = this@RateDto.rateUsd
        symbol = this@RateDto.symbol
        type = this@RateDto.type
        currencySymbol = this@RateDto.currencySymbol
    }
}

fun List<RateDto>.toEntity() = map { it.toEntity() }.toRealmList()