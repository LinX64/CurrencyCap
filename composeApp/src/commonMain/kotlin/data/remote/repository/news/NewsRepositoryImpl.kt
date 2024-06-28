package data.remote.repository.news

import data.remote.model.news.Article
import data.remote.model.news.News
import data.util.APIConst.NEWS_URL
import data.util.retryOnIOException
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
    private val httpClient: HttpClient
) : NewsRepository {

    override fun getNews(): Flow<List<Article>> = flow {
        val responseText: String = httpClient.get(NEWS_URL).bodyAsText()
        val news: News = Json.decodeFromString(News.serializer(), responseText)
        emit(news.articles)
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()

    override fun getArticleByUrl(url: String): Flow<Article> = flow {
        val responseText: String = httpClient.get(NEWS_URL).bodyAsText()
        val articles: List<Article> = Json.decodeFromString(News.serializer(), responseText).articles
        emit(articles.first { it.url == url })
    }
        .flowOn(Dispatchers.IO)
        .retryOnIOException()
}