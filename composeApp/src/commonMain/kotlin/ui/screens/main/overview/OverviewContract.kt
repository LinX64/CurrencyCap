package ui.screens.main.overview

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import domain.model.Article
import domain.model.main.BonbastRate
import domain.model.main.Crypto
import domain.model.main.Market
import domain.model.main.Rate
import kotlinx.collections.immutable.ImmutableList

sealed interface OverviewViewEvent {
    data object OnLoadRates : OverviewViewEvent
    data object OnRetry : OverviewViewEvent
}

sealed interface OverviewState {
    data object Loading : OverviewState

    @Stable
    data class Success(
        val bonbastRates: ImmutableList<BonbastRate>,
        val cryptoRates: ImmutableList<Crypto>,
        val markets: ImmutableList<Market>,
        val fiatRates: ImmutableList<Rate>,
        val topMovers: ImmutableList<Crypto>,
        val news: ImmutableList<Article>
    ) : OverviewState

    @Immutable
    data class Error(val message: String) : OverviewState
}

sealed interface OverviewNavigationEffect
