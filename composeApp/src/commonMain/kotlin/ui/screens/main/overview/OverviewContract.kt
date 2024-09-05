package ui.screens.main.overview

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import domain.model.main.Currencies

sealed interface OverviewViewEvent {
    data class OnLoadRates(val forceRefresh: Boolean = false) : OverviewViewEvent
    data object OnRetry : OverviewViewEvent
}

sealed interface OverviewState {
    data object Loading : OverviewState

    @Stable
    data class Success(
        val combinedRates: Currencies
    ) : OverviewState

    @Immutable
    data class Error(val message: String) : OverviewState
}

sealed interface OverviewNavigationEffect
