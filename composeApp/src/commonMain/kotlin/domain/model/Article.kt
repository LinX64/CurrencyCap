package domain.model

import androidx.compose.runtime.Stable
import data.local.model.ArticleEntity

@Stable
data class Article(
    val author: String? = null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String? = null,
    val isBookmarked: Boolean
)

fun List<Article>.toEntity(): List<ArticleEntity> = map { article ->
    ArticleEntity().apply {
        author = article.author ?: ""
        description = article.description
        publishedAt = article.publishedAt
        title = article.title
        url = article.url
        sourceName = article.source.name
        urlToImage = article.urlToImage ?: ""
        isBookmarked = article.isBookmarked
    }
}