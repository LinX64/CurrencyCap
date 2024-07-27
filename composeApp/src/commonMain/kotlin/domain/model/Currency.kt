package domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Currency(
    val code: String,
    val value: Double
)