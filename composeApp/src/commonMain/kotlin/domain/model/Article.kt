package domain.model

data class Article(
    val author: String? = null,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceDto: Source,
    val title: String,
    val url: String,
    val urlToImage: String? = null
)