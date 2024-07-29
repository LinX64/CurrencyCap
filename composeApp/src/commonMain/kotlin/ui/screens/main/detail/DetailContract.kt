package ui.screens.main.detail

import androidx.compose.runtime.Stable
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import domain.model.main.CryptoInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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
        val chartData: ChartDataUiState = ChartDataUiState()
    ) : DetailState

    @Stable
    data class Error(val message: String) : DetailState
}

@Stable
data class ChartDataUiState(
    val chartDataPoints: ImmutableList<ChartDataPoint> = persistentListOf(),
    val isLoading: Boolean = false
)

sealed interface DetailNavigationEffect