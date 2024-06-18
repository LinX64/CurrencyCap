package ui.screens.overview

import domain.model.DataDao
import domain.model.RateDao

sealed interface OverviewViewEvent {
    data object OnLoadRates : OverviewViewEvent
    data object OnRefreshRates : OverviewViewEvent
}

sealed interface OverviewNavigationEffect

sealed interface OverviewState {
    data object Loading : OverviewState

    data class Success(
        val iranianRate: List<RateDao>,
        val cryptoRates: List<DataDao>,
        val topMovers: List<TopMovers>
    ) : OverviewState

    data class Error(val message: String) : OverviewState
}

data class TopMovers(
    val symbol: String,
    val rateUsd: String
)