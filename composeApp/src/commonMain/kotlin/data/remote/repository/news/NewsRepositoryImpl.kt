package data.remote.repository.news

import data.remote.model.news.ArticleDto
import data.remote.model.news.NewsDto
import data.remote.model.news.toDomain
import data.remote.model.news.toEntity
import data.util.APIConst.NEWS_URL
import data.util.networkBoundNetworkResult
import data.util.retryOnIOException
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val articleLocalDataSource: ArticleLocalDataSource
) : NewsRepository {

    override fun getNews() = networkBoundNetworkResult(
        query = { articleLocalDataSource.getArticles() },
        fetch = { getPlainNewsResponse() },
        saveFetchResult = { responseText ->
            val articles: List<ArticleDto> = Json.decodeFromString(NewsDto.serializer(), responseText).articles
            articleLocalDataSource.insertArticles(articles.toEntity())
        }
    )

    override fun getArticleByUrl(url: String): Flow<Article> = flow {
        val responseText = getPlainNewsResponse()
        val articles: List<ArticleDto> = Json.decodeFromString(NewsDto.serializer(), responseText).articles

        val matchedArticle = articles
            .find { it.url.contains(url) }
            ?.toDomain()
            ?: throw NoSuchElementException("No article found with URL: $url")

        emit(matchedArticle)
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    private suspend fun getPlainNewsResponse() = httpClient.get(NEWS_URL).bodyAsText()
}