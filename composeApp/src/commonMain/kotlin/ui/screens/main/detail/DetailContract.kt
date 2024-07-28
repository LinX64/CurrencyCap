package ui.screens.main.detail

import androidx.compose.runtime.Stable
import data.remote.model.main.CryptoInfo
import domain.model.ChartDataPoint
import domain.model.ChipPeriod
import domain.model.main.Crypto
import kotlinx.collections.immutable.ImmutableList

sealed interface DetailViewEvent {
    data object OnRetry : DetailViewEvent
    data object OnLoadCryptoInfo : DetailViewEvent

    data class OnChartPeriodSelect(
        val coinId: String,
        val chipPeriod: ChipPeriod
    ) : DetailViewEvent
}

sealed interface DetailState {
    data object Idle : DetailState
    data object Loading : DetailState

    @Stable
    data class Success(
        val crypto: Crypto,
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