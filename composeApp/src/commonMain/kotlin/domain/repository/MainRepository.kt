package domain.repository

import data.remote.model.main.Description
import data.remote.model.news.ArticleDto
import domain.model.main.Crypto
import domain.model.main.Currencies
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getAllRates(): Flow<Currencies>

    fun getCryptoBySymbol(symbol: String): Flow<Crypto>
    fun getCryptoInfoBySymbol(symbol: String): Flow<Description>
    suspend fun getCryptoNameBySymbol(symbol: String): String

    fun search(query: String): Flow<List<Crypto>>
    fun getNews(): Flow<List<ArticleDto>>
}