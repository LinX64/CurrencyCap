package ui.screens.main.detail

import androidx.compose.runtime.Stable
import domain.model.main.Crypto

sealed interface DetailViewEvent {
    data object OnRetry : DetailViewEvent
    data object OnLoadCryptoInfo : DetailViewEvent
}

sealed interface DetailState {
    data object Idle : DetailState
    data object Loading : DetailState

    @Stable
    data class Success(
        val crypto: Crypto,
        val description: String
    ) : DetailState

    @Stable
    data class Error(val message: String) : DetailState
}

sealed interface DetailNavigationEffect