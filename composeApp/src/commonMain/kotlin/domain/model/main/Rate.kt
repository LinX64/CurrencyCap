package domain.model.main

import kotlinx.serialization.Serializable

@Serializable
data class Rate(
    val id: String,
    val rateUsd: String,
    val symbol: String,
    val type: String,
    val currencySymbol: String? = null
)