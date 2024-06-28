package data.local.model

import data.remote.model.news.ArticleDto
import data.remote.model.news.SourceDto
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
}

fun ArticleEntity.toDomain() = ArticleDto(
    title = title,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    url = url,
    urlToImage = urlToImage,
    sourceDto = SourceDto(name = sourceName)
)