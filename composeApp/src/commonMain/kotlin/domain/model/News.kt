package domain.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class News(
    val articles: ImmutableList<Article>,
    val status: String,
    val totalResults: Int
)
