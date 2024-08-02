package ui.screens.main.detail

import androidx.compose.runtime.Stable
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.model.main.CryptoInfo

sealed interface DetailViewEvent {
    data object OnRetry : DetailViewEvent
    data object OnLoadCryptoInfo : DetailViewEvent

    @Stable
    data class OnChartPeriodSelect(
        val coinId: String,
        val symbol: String,
        val chipPeriod: ChipPeriod,
    ) : DetailViewEvent
}

sealed interface DetailState {
    data object Idle : DetailState
    data object Loading : DetailState

    @Stable
    data class Success(
        val cryptoInfo: CryptoInfo,
    ) : DetailState

    @Stable
    data class Error(val message: String) : DetailState
}

@Stable
data class ChartDataUiState(
    val chartDataPoints: Set<ChartDataPoint>? = null,
    val isLoading: Boolean = false
)

sealed interface DetailNavigationEffect