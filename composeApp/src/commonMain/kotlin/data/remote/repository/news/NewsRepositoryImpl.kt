package data.remote.repository.news

import data.remote.model.news.ArticleDto
import data.remote.model.news.NewsDto
import data.remote.model.news.toDomain
import data.remote.model.news.toEntity
import data.util.APIConst.NEWS_URL
import data.util.NetworkResult
import data.util.cacheDataOrFetchOnline
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val articleLocalDataSource: ArticleLocalDataSource
) : NewsRepository {

    override fun getNews(): Flow<NetworkResult<List<Article>>> = cacheDataOrFetchOnline(
        query = { articleLocalDataSource.getArticles() },
        fetch = { getPlainNewsResponse() },
        shouldFetch = { localArticles -> localArticles?.isEmpty() == true }, // TODO: Implement a check for stale data
        saveFetchResult = { responseText ->
            val articles: List<ArticleDto> = parseNewsResponse(responseText)
            articleLocalDataSource.insertArticles(articles.toEntity())
        }
    )

    override fun getArticleByUrl(url: String): Flow<NetworkResult<Article>> = cacheDataOrFetchOnline(
        query = { articleLocalDataSource.getArticleByUrl(url) },
        fetch = { fetchArticleByUrl(url) },
        shouldFetch = { localArticle -> localArticle == null },
        saveFetchResult = { article ->
            articleLocalDataSource.insertArticle(article.toEntity())
        }
    )

    private suspend fun getPlainNewsResponse() = httpClient.get(NEWS_URL).bodyAsText()

    private fun parseNewsResponse(responseText: String): List<ArticleDto> {
        return Json.decodeFromString(NewsDto.serializer(), responseText).articles
    }

    private suspend fun fetchArticleByUrl(url: String): Article {
        val responseText = getPlainNewsResponse()
        val articles: List<ArticleDto> = parseNewsResponse(responseText)

        val matchedArticle = articles
            .find { it.url.contains(url) }
            ?.toDomain()
            ?: throw NoSuchElementException("No article found with URL: $url")

        return matchedArticle
    }
}