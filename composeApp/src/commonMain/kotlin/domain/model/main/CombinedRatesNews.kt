package domain.model.main

import androidx.compose.runtime.Stable
import domain.model.Article
import kotlinx.collections.immutable.ImmutableList

@Stable
data class CombinedRatesNews(
    val bonbastRates: ImmutableList<BonbastRate>,
    val cryptoRates: ImmutableList<Crypto>,
    val markets: ImmutableList<Market>,
    val fiatRates: ImmutableList<Rate>,
    val topMovers: ImmutableList<Crypto>,
    val news: ImmutableList<Article>
)