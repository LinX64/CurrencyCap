package data.local.model

import domain.model.Article
import domain.model.Source
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNull.content

@Serializable
open class ArticleEntity : RealmObject {
    @PrimaryKey
    var url: String = ""
    var title: String = ""
    var description: String = ""
    var author: String = ""
    var publishedAt: String = ""
    var urlToImage: String = ""
    var sourceName: String = ""
    var isBookmarked: Boolean = false

    // Temporary workaround for [K2] IllegalStateException: Multiple plugins generated nested...
    companion object
}

fun ArticleEntity.toDomain() = Article(
    title = title,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    url = url,
    urlToImage = urlToImage,
    source = Source(name = sourceName),
    isBookmarked = isBookmarked
)

fun List<ArticleEntity>.toDomain() = map(ArticleEntity::toDomain)