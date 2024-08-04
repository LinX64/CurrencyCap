package domain.model.main

import androidx.compose.runtime.Stable

@Stable
data class BonbastRate(
    val code: String,
    val sell: Double,
    val buy: Double,
    val imageUrl: String,
)
