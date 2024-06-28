package data.remote.model.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    @SerialName("articles")
    val articles: List<Article>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int
)

@Serializable
data class Article(
    @SerialName("author")
    val author: String? = null,
    @SerialName("content")
    val content: String,
    @SerialName("description")
    val description: String,
    @SerialName("publishedAt")
    val publishedAt: String,
    @SerialName("source")
    val source: Source? = null,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("urlToImage")
    val urlToImage: String? = null,
    val isBookmarked: Boolean
)

@Serializable
data class Source(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String
)