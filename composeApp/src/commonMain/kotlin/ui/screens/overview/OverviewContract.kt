package ui.screens.overview

import data.model.BonbastRate
import data.model.Crypto
import data.model.Market
import data.model.Rate

sealed interface OverviewViewEvent {
    data object OnLoadRates : OverviewViewEvent
    data object OnRefreshRates : OverviewViewEvent
}

sealed interface OverviewNavigationEffect

sealed interface OverviewState {
    data object Loading : OverviewState

    data class Success(
        val bonbastRates: List<BonbastRate>,
        val cryptoRates: List<Crypto>,
        val markets: List<Market>,
        val fiatRates: List<Rate>
    ) : OverviewState

    data class Error(val message: String) : OverviewState
}

data class TopMovers(
    val symbol: String,
    val rateUsd: String
)