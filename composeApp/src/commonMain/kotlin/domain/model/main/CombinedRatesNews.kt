package domain.model.main

import androidx.compose.runtime.Stable
import domain.model.Article

@Stable
data class CombinedRatesNews(
    val bonbastRates: List<BonbastRate>,
    val cryptoRates: List<Crypto>,
    val markets: List<Market>,
    val fiatRates: List<Rate>,
    val topMovers: List<Crypto>,
    val news: List<Article>
)