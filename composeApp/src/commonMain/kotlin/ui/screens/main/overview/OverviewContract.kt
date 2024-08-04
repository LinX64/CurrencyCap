package ui.screens.main.overview

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import domain.model.Article
import domain.model.main.BonbastRate
import domain.model.main.Crypto
import domain.model.main.Market
import domain.model.main.Rate

sealed interface OverviewViewEvent {
    data class OnLoadRates(val forceRefresh: Boolean = false) : OverviewViewEvent
    data object OnRetry : OverviewViewEvent
}

sealed interface OverviewState {
    data object Loading : OverviewState

    @Stable
    data class Success(
        val bonbastRates: List<BonbastRate>,
        val cryptoRates: List<Crypto>,
        val markets: List<Market>,
        val fiatRates: List<Rate>,
        val topMovers: List<Crypto>,
        val news: List<Article>
    ) : OverviewState

    @Immutable
    data class Error(val message: String) : OverviewState
}

sealed interface OverviewNavigationEffect
