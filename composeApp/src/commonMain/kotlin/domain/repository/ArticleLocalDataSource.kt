package domain.repository

import data.local.model.ArticleEntity
import domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    suspend fun insertArticle(article: ArticleEntity)
    suspend fun updateArticle(article: ArticleEntity)
    suspend fun removeArticle(article: Article)
    suspend fun removeArticleByUrl(url: String)

    fun getArticleByUrl(url: String): Flow<Article?>

    suspend fun insertArticles(articles: Set<ArticleEntity>)
    fun getArticles(): Flow<List<Article>>

    fun getBookmarkedArticles(): Flow<List<Article>>

    suspend fun deleteArticles()
}