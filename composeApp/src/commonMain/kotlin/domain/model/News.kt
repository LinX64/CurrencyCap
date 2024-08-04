package domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
