package domain.model

import androidx.compose.runtime.Stable

@Stable
data class CurrencyRate(
    val code: String,
    val value: Double
)