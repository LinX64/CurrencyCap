package domain.model.main

import androidx.compose.runtime.Immutable

@Immutable
data class Data(
    val currencySymbol: String? = null,
    val id: String,
    val rateUsd: String,
    val symbol: String,
    val type: String
)

