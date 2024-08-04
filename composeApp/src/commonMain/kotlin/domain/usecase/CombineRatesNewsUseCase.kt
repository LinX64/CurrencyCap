package domain.usecase

import data.util.NetworkResult.Error
import data.util.NetworkResult.Success
import domain.model.main.CombinedRatesNews
import domain.model.main.Crypto
import domain.repository.MainRepository
import domain.repository.NewsRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlin.math.abs

class CombineRatesNewsUseCase(
    private val mainRepository: MainRepository,
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        forceRefresh: Boolean = false
    ): Flow<CombinedRatesNews> = coroutineScope {
        val ratesFlow = mainRepository.getAllRates(forceRefresh)
            .mapNotNull { (it as? Success)?.data ?: (it as? Error)?.data }

        val newsFlow = newsRepository.getNews(forceRefresh)
            .mapNotNull { (it as? Success)?.data ?: (it as? Error)?.data }
            .map { it.take(2) }

        combine(ratesFlow, newsFlow) { rates, news ->
            CombinedRatesNews(
                bonbastRates = rates.bonbast,
                cryptoRates = rates.crypto.sortedBy { it.name },
                markets = rates.markets,
                fiatRates = rates.rates.filter { it.type == FIAT },
                topMovers = mapToTopMovers(rates.crypto),
                news = news
            )
        }
    }

    private companion object {
        private const val FIAT = "fiat"
    }
}

private fun mapToTopMovers(rates: List<Crypto>) = rates
    .sortedBy { it.symbol }
    .sortedByDescending { abs(it.priceChangePercentage24h) }
    .take(2)

