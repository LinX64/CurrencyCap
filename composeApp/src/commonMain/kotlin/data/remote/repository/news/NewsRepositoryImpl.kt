package data.remote.repository.news

import data.remote.model.news.ArticleDto
import data.remote.model.news.NewsDto
import data.remote.model.news.toDomain
import data.remote.model.news.toEntity
import data.remote.model.requests.GetNews
import data.util.parseResponse
import di.baseApi
import domain.model.Article
import domain.model.toEntity
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val articleLocalDataSource: ArticleLocalDataSource,
) : NewsRepository {

    override suspend fun getNewsNew(): Store<String, List<Article>> {
        return StoreBuilder.from(
            fetcher = Fetcher.of { getNewsOnline() },
            sourceOfTruth = SourceOfTruth.of(
                reader = { articleLocalDataSource.getArticles() },
                writer = { _: String, response: List<Article> ->
                    val sortedArticles = response.sortedBy { it.publishedAt }.reversed()
                    articleLocalDataSource.insertArticles(sortedArticles.toEntity().toSet())
                },
                deleteAll = { articleLocalDataSource.deleteArticles() },
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

    override suspend fun getNewsOnline(): List<Article> {
        val response = httpClient.get(GetNews()) { baseApi() }
        val articles: List<ArticleDto> = parseResponse<NewsDto>(response).articles
        return articles.map(ArticleDto::toDomain)
    }

    private suspend fun fetchArticleByUrl(url: String): Article {
        val response = httpClient.get(GetNews()) { baseApi() }
        val articles: List<ArticleDto> = parseResponse<NewsDto>(response).articles

        val matchedArticle = articles
            .find { it.url.contains(url) }
            ?.toDomain()
            ?: throw NoSuchElementException("No article found with URL: $url")

        return matchedArticle
    }
}