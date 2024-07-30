package domain.repository

import data.util.NetworkResult
import domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsOnline(): Flow<List<Article>>
    fun getNews(): Flow<NetworkResult<List<Article>>>
    fun getArticleByUrl(url: String): Flow<NetworkResult<Article>>
}