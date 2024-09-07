package domain.repository

import domain.model.Article
import org.mobilenativefoundation.store.store5.Store

interface NewsRepository {
    suspend fun getNewsOnline(): List<Article>
    suspend fun getNewsNew(): Store<String, List<Article>>
    fun getArticleByUrlNew(url: String): Store<Any, Article>
}