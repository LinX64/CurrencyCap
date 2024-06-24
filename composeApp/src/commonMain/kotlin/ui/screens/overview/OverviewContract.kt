package ui.screens.overview

import domain.model.BonbastRateDto
import domain.model.MarketDto
import domain.model.RateDto

sealed interface OverviewViewEvent {
    data object OnLoadRates : OverviewViewEvent
    data object OnRefreshRates : OverviewViewEvent
}

sealed interface OverviewNavigationEffect

sealed interface OverviewState {
    data object Loading : OverviewState

    data class Success(
        val bonbastRates: List<BonbastRateDto>,
        val cryptoRates: List<RateDto>,
        val markets: List<MarketDto>,
        val fiatRates: List<RateDto>,
        val topMovers: List<TopMovers>
    ) : OverviewState

    data class Error(val message: String) : OverviewState
}

data class TopMovers(
    val symbol: String,
    val rateUsd: String
)