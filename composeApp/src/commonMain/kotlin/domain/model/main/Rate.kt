package domain.model.main

data class Rate(
    val id: String,
    val rateUsd: String,
    val symbol: String,
    val type: String,
    val currencySymbol: String? = null
)