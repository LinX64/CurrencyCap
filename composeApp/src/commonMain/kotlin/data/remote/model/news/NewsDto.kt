package data.remote.model.news

import data.local.model.ArticleEntity
import domain.model.Article
import domain.model.News
import domain.model.Source
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    @SerialName("articles")
    val articles: List<ArticleDto>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int
)

@Serializable
data class ArticleDto(
    @SerialName("author")
    val author: String? = null,
    @SerialName("content")
    val content: String,
    @SerialName("description")
    val description: String,
    @SerialName("publishedAt")
    val publishedAt: String,
    @SerialName("source")
    val sourceDto: SourceDto,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("urlToImage")
    val urlToImage: String? = null,
) {
    fun toEntity(): ArticleEntity {
        return ArticleEntity().apply {
            url = this@ArticleDto.url
            title = this@ArticleDto.title
            description = this@ArticleDto.description
            author = this@ArticleDto.author ?: ""
            publishedAt = this@ArticleDto.publishedAt
            sourceName = this@ArticleDto.sourceDto.name
            urlToImage = this@ArticleDto.urlToImage ?: ""
        }
    }
}

@Serializable
data class SourceDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String
)

fun NewsDto.toDomain(): News {
    return News(
        articles = articles.map { it.toDomain() }.toImmutableList(),
        status = status,
        totalResults = totalResults
    )
}

fun ArticleDto.toDomain(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = sourceDto.toDomain(),
        title = title,
        url = url,
        urlToImage = urlToImage,
        isBookmarked = false
    )
}

fun SourceDto.toDomain() = Source(
    id = id.orEmpty(),
    name = name
)

fun List<ArticleDto>.toDomain(): List<Article> {
    return map { it.toDomain() }
}

fun Article.toEntity() = ArticleEntity().apply {
    url = this@toEntity.url
    title = this@toEntity.title
    description = this@toEntity.description
    author = this@toEntity.author.orEmpty()
    publishedAt = this@toEntity.publishedAt
    sourceName = this@toEntity.source.name
    urlToImage = this@toEntity.urlToImage.orEmpty()
    isBookmarked = this@toEntity.isBookmarked
}

fun List<ArticleDto>.toEntity(): List<ArticleEntity> {
    return map { dto ->
        ArticleEntity().apply {
            url = dto.url
            title = dto.title
            description = dto.description
            author = dto.author.orEmpty()
            publishedAt = dto.publishedAt
            sourceName = dto.sourceDto.name
            urlToImage = dto.urlToImage.orEmpty()
        }
    }
}