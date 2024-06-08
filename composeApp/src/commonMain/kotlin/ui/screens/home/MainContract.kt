package ui.screens.home

import domain.model.DataDao
import domain.model.RateDao

sealed interface MainViewEvent {
    data object LoadRates : MainViewEvent
    data object RefreshRates : MainViewEvent
}

sealed interface MainNavigationEffect

sealed interface MainState {
    data object Loading : MainState

    data class Success(
        val iranianRate: List<RateDao>,
        val cryptoRates: List<DataDao>,
        val topMovers: List<TopMovers>
    ) : MainState

    data class Error(val error: String) : MainState
}

data class TopMovers(
    val symbol: String,
    val rateUsd: String
)