package domain.repository

import data.local.model.ArticleEntity
import domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    suspend fun insertArticle(article: ArticleEntity)
    suspend fun removeArticle(article: Article)
    fun readArticles(): Flow<List<Article>>
    suspend fun cleanUp()
}