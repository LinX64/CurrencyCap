package ui.screens.main.crypto_list

import androidx.compose.runtime.Stable
import domain.model.main.Crypto

sealed interface CryptoListViewEvent {
    data object OnGetCryptoList : CryptoListViewEvent
    data object OnRetry : CryptoListViewEvent
}

sealed interface CryptoListState {
    data object Idle : CryptoListState
    data object Loading : CryptoListState

    @Stable
    data class Success(val cryptoList: List<Crypto>) : CryptoListState

    @Stable
    data class Error(val message: String) : CryptoListState
}

sealed interface CryptoListNavigationEffect {
    data class ShowToast(val message: String) : CryptoListNavigationEffect
}