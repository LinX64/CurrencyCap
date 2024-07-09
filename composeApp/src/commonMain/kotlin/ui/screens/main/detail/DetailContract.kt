package ui.screens.main.detail

import domain.model.main.Crypto

sealed interface DetailViewEvent {
    data object OnLoadCryptoDetail : DetailViewEvent
}

sealed interface DetailState {
    data object Idle : DetailState
    data object Loading : DetailState
    data class Success(val crypto: Crypto) : DetailState
    data class Error(val message: String) : DetailState
}

sealed interface DetailNavigationEffect {

}