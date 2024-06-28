package domain.repository

import data.local.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    suspend fun insertArticle(article: ArticleEntity)
    fun readArticles(): Flow<List<ArticleEntity>>
    suspend fun cleanUp()
}