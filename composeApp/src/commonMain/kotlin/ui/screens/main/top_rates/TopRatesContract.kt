package ui.screens.main.top_rates

import androidx.compose.runtime.Stable
import domain.model.main.BonbastRate

sealed interface TopRatesViewEvent {
    data object OnGetTopRates : TopRatesViewEvent
    data object OnRetry : TopRatesViewEvent
}

sealed interface TopRatesState {
    data object Idle : TopRatesState
    data object Loading : TopRatesState

    @Stable
    data class Success(val topRates: List<BonbastRate>) : TopRatesState

    @Stable
    data class Error(val message: String) : TopRatesState
}

sealed interface TopRatesNavigationEffect {
    data class ShowToast(val message: String) : TopRatesNavigationEffect
}