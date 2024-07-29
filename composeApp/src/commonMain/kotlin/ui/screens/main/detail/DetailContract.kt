package ui.screens.main.detail

import androidx.compose.runtime.Stable
import data.remote.model.main.CryptoInfo
import domain.model.ChipPeriod
import domain.model.main.ChartDataPoint
import kotlinx.collections.immutable.ImmutableList

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
        val chartData: ChartDataUiState? = null
    ) : DetailState

    @Stable
    data class Error(val message: String) : DetailState
}

@Stable
data class ChartDataUiState(
    val data: ImmutableList<ChartDataPoint>,
    val isLoading: Boolean = false
)

sealed interface DetailNavigationEffect