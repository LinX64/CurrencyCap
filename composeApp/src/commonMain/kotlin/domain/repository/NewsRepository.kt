package domain.repository

import data.util.NetworkResult
import domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<NetworkResult<List<Article>>>
    fun getArticleByUrl(url: String): Flow<Article>
}