package data.remote.model.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rate(
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
)