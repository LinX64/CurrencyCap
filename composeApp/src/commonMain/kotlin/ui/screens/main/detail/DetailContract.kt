package ui.screens.main.detail

import androidx.compose.runtime.Stable
import data.remote.model.main.CryptoInfo
import domain.model.ChipPeriod
import domain.model.CoinMarketChartData
import domain.model.main.Crypto

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
        val crypto: Crypto? = null,
        val cryptoInfo: CryptoInfo? = null,
        val chartData: CoinMarketChartData? = null
    ) : DetailState

    @Stable
    data class Error(val message: String) : DetailState
}

sealed interface DetailNavigationEffect