package ui.screens.main.search

import domain.model.main.Crypto
import kotlinx.collections.immutable.ImmutableList

sealed interface SearchEvent {
    data class OnSearchTextChanged(val query: String) : SearchEvent
    data class OnSearchClicked(val query: String) : SearchEvent
    data class OnRetryClicked(val query: String) : SearchEvent
}

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data object Empty : SearchState
    data class Success(val cryptoList: ImmutableList<Crypto>) : SearchState
    data class Error(val message: String) : SearchState
}

sealed interface SearchNavigationEffect

