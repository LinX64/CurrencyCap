package domain.repository

import data.model.news.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<Article>>
    fun getArticleByUrl(url: String): Flow<Article>
}