package domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Source(
    val id: String? = null,
    val name: String
)
