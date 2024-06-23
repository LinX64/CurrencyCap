package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RateDto(
    val id: String,
    val rateUsd: String,
    val symbol: String,
    val type: String,
    val currencySymbol: String? = null
)