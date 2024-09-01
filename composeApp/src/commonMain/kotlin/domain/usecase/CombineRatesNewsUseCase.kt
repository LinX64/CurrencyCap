package domain.usecase

import data.util.Constant.ALL_RATES_KEY
import data.util.Constant.NEWS_KEY
import domain.model.main.CombinedRatesNews
import domain.model.main.Crypto
import domain.repository.MainRepository
import domain.repository.NewsRepository
import kotlinx.coroutines.coroutineScope
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import kotlin.math.abs

class CombineRatesNewsUseCase(
    private val mainRepository: MainRepository,
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(isRefreshing: Boolean = false) = coroutineScope {
        val getAllRates = mainRepository.getAllRatesNew()
            .fresh(key = ALL_RATES_KEY)
        val getNews = newsRepository.getNewsNew()
            .fresh(NEWS_KEY)

        CombinedRatesNews(
            bonbastRates = getAllRates.bonbast,
            cryptoRates = getAllRates.crypto.sortedBy { it.name },
            markets = getAllRates.markets,
            fiatRates = getAllRates.rates.filter { it.type == FIAT },
            topMovers = mapToTopMovers(getAllRates.crypto),
            news = getNews.take(2)
        )
    }

    private companion object {
        private const val FIAT = "fiat"
    }
}

private fun mapToTopMovers(rates: List<Crypto>) = rates
    .sortedBy { it.symbol }
    .sortedByDescending { abs(it.priceChangePercentage24h) }
    .take(2)

