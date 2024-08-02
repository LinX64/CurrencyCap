package data.remote.repository.news

import data.local.datastore.app.AppPreferences
import data.remote.model.news.ArticleDto
import data.remote.model.news.NewsDto
import data.remote.model.news.toDomain
import data.remote.model.news.toEntity
import data.util.APIConst.NEWS_URL
import data.util.NetworkResult
import data.util.cacheDataOrFetchOnline
import data.util.retryOnIOException
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val appPreferences: AppPreferences,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : NewsRepository {

    private val cacheExpirationTime = 15.seconds.inWholeSeconds
    private var cachedLastFetchTime: Long = 0

    init {
        updateCachedLastFetchTime()
    }

    override fun getNews(forceRefresh: Boolean): Flow<NetworkResult<List<Article>>> = cacheDataOrFetchOnline(
        forceRefresh = forceRefresh,
        query = { articleLocalDataSource.getArticles() },
        fetch = { getPlainNewsResponse() },
        shouldFetch = { localArticles -> localArticles.isNullOrEmpty() || isCacheExpired() },
        clearLocalData = { articleLocalDataSource.deleteArticles() },
        isFresh = { localData ->
            localData != null && (localData as? Collection<*>)?.isNotEmpty() == true && !isCacheExpired()
        },
        saveFetchResult = { responseText ->
            val articles: Set<ArticleDto> = parseArticlesResponse(responseText)
            val sortedArticles = articles.sortedBy { it.publishedAt }.reversed().toSet()
            articleLocalDataSource.insertArticles(sortedArticles.toEntity())

            println("cachedLastFetchTime: $cachedLastFetchTime")
            println("getCurrentTime(): ${getCurrentTime()}")

            appPreferences.saveLastFetchTime(getCurrentTime())
        }
    )

    override fun getArticleByUrl(url: String): Flow<NetworkResult<Article>> = cacheDataOrFetchOnline(
        query = { articleLocalDataSource.getArticleByUrl(url) },
        fetch = { fetchArticleByUrl(url) },
        shouldFetch = { localArticle -> localArticle == null },
        forceRefresh = false,
        clearLocalData = { },
        saveFetchResult = { article ->
            articleLocalDataSource.insertArticle(article.toEntity())
        }
    )

    private suspend fun getPlainNewsResponse() = httpClient.get(NEWS_URL).bodyAsText()

    private fun parseArticlesResponse(responseText: String): Set<ArticleDto> {
        return Json.decodeFromString(NewsDto.serializer(), responseText).articles.toSet()
    }

    override fun getNewsOnline(): Flow<List<Article>> = flow {
        val response = httpClient.get(NEWS_URL).body<Set<ArticleDto>>().toDomain()
        emit(response)
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    private suspend fun fetchArticleByUrl(url: String): Article {
        val responseText = getPlainNewsResponse()
        val articles: Set<ArticleDto> = parseArticlesResponse(responseText)

        val matchedArticle = articles
            .find { it.url.contains(url) }
            ?.toDomain()
            ?: throw NoSuchElementException("No article found with URL: $url")

        return matchedArticle
    }

    private fun updateCachedLastFetchTime() {
        coroutineScope.launch {
            cachedLastFetchTime = appPreferences.getLastFetchTime()
        }
    }

    private fun isCacheExpired(): Boolean {
        val now = getCurrentTime()
        val isExpired = now - cachedLastFetchTime > cacheExpirationTime
        return isExpired
    }

    private fun getCurrentTime(): Long = Clock.System.now().toEpochMilliseconds()
}