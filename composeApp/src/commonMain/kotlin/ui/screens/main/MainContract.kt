package ui.screens.main

import domain.model.DataDao
import domain.model.RateDao

sealed interface MainViewEvent {
    data object LoadIranianRates : MainViewEvent
    data object LoadCryptoRates : MainViewEvent

    data object RefreshRates : MainViewEvent
    data class ShowError(val error: Throwable) : MainViewEvent
}

sealed interface NavigationEffect

sealed interface MainState {
    data object Loading : MainState

    data class Success(val ratesList: List<RateDao>) : MainState
    data class Error(val error: String) : MainState

    data class CryptoRatesSuccess(val ratesList: List<DataDao>) : MainState
    data class CryptoRatesError(val error: String) : MainState
}
