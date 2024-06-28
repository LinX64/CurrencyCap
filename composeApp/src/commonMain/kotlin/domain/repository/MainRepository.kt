package domain.repository

import data.remote.model.news.Article
import domain.model.CurrenciesDto
import domain.model.RateDto
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getAllRates(): Flow<CurrenciesDto>
    fun search(query: String): Flow<List<RateDto>>
    fun getNews(): Flow<List<Article>>
}