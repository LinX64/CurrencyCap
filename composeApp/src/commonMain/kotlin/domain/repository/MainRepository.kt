package domain.repository

import data.remote.model.news.ArticleDto
import domain.model.main.Currencies
import domain.model.main.Rate
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getAllRates(): Flow<Currencies>
    fun search(query: String): Flow<List<Rate>>
    fun getNews(): Flow<List<ArticleDto>>
}