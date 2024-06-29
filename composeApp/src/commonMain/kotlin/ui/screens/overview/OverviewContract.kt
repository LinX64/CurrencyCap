package ui.screens.overview

import domain.model.main.BonbastRate
import domain.model.main.Crypto
import domain.model.main.Market
import domain.model.main.Rate

sealed interface OverviewViewEvent {
    data object OnLoadRates : OverviewViewEvent
    data object OnRefreshRates : OverviewViewEvent
}

sealed interface OverviewNavigationEffect

sealed interface OverviewState {
    data object Loading : OverviewState

    data class Success(
        val bonbastRates: List<BonbastRate>,
        val cryptoRates: List<Rate>,
        val markets: List<Market>,
        val fiatRates: List<Rate>,
        val topMovers: List<Crypto>
    ) : OverviewState

    data class Error(val message: String) : OverviewState
}
