package data.remote.repository.news

import data.remote.model.news.ArticleDto
import data.remote.model.news.NewsDto
import data.remote.model.news.toDomain
import data.remote.model.news.toEntity
import data.remote.model.requests.GetNews
import data.util.APIConst.NEWS_URL
import data.util.retryOnIOException
import domain.model.Article
import domain.model.toEntity
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val articleLocalDataSource: ArticleLocalDataSource,
) : NewsRepository {

    override fun getNewsNew(): Store<String, List<Article>> {
        return StoreBuilder.from(
            fetcher = Fetcher.ofFlow { getNewsOnline() },
            sourceOfTruth = SourceOfTruth.of(
                reader = { articleLocalDataSource.getArticles() },
                writer = { _: String, response: List<Article> ->
                    val sortedArticles = response.sortedBy { it.publishedAt }.reversed()
                    articleLocalDataSource.insertArticles(sortedArticles.toEntity().toSet())
                },
                deleteAll = { articleLocalDataSource.deleteArticles() }
            )
        ).build()
    }

    override fun getArticleByUrlNew(url: String): Store<Any, Article> {
        return StoreBuilder.from(
            fetcher = Fetcher.of { fetchArticleByUrl(url) },
            sourceOfTruth = SourceOfTruth.of(
                reader = { articleLocalDataSource.getArticleByUrl(url) },
                writer = { _, response -> articleLocalDataSource.insertArticle(response.toEntity()) },
                delete = { articleLocalDataSource.removeArticleByUrl(url) }
            )
        ).build()
    }

    private suspend fun getPlainNewsResponse() = httpClient.get(NEWS_URL).bodyAsText()

    private fun parseArticlesResponse(responseText: String): List<ArticleDto> {
        return Json.decodeFromString(NewsDto.serializer(), responseText).articles
    }

    override fun getNewsOnline(): Flow<List<Article>> = flow {
        val response = httpClient.get(GetNews())
        when (response.status.isSuccess()) {
            true -> {
                val responseText = response.bodyAsText()
                val articles: List<ArticleDto> = parseArticlesResponse(responseText)
                emit(articles.map { it.toDomain() })
            }

            false -> emit(emptyList())
        }
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    private suspend fun fetchArticleByUrl(url: String): Article {
        val responseText = getPlainNewsResponse()
        val articles: List<ArticleDto> = parseArticlesResponse(responseText)

        val matchedArticle = articles
            .find { it.url.contains(url) }
            ?.toDomain()
            ?: throw NoSuchElementException("No article found with URL: $url")

        return matchedArticle
    }
}