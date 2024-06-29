package domain.model

import data.local.model.ArticleEntity

data class Article(
    val author: String? = null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceDto: Source,
    val title: String,
    val url: String,
    val urlToImage: String? = null,
    val isBookmarked: Boolean = false
)

fun List<Article>.toEntity(): List<ArticleEntity> = map { article ->
    ArticleEntity().apply {
        author = article.author ?: ""
        description = article.description
        publishedAt = article.publishedAt
        title = article.title
        url = article.url
        urlToImage = article.urlToImage ?: ""
        isBookmarked = article.isBookmarked
    }
}