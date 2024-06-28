package domain.model.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("currencySymbol")
    val currencySymbol: String? = null,
    @SerialName("id")
    val id: String,
    @SerialName("rateUsd")
    val rateUsd: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("type")
    val type: String
)

