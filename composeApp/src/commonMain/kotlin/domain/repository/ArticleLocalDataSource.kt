package domain.repository

import data.local.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    suspend fun insertCurrencyData(currency: ArticleEntity)
    fun readCurrencyData(): Flow<List<ArticleEntity>>
    suspend fun cleanUp()
}