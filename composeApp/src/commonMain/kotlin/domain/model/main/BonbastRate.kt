package domain.model.main

import androidx.compose.runtime.Immutable

@Immutable
data class BonbastRate(
    val code: String,
    val sell: Double,
    val buy: Double
)
