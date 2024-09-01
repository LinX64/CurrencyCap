package domain.repository

import data.util.NetworkResult
import domain.model.Article
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.Store

interface NewsRepository {
    fun getNewsOnline(): Flow<List<Article>>
    fun getNewsNew(): Store<String, List<Article>>
    fun getArticleByUrl(url: String): Flow<NetworkResult<Article>>
}