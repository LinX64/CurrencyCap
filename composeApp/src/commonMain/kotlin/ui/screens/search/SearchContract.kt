package ui.screens.search

import domain.model.DataDao

sealed interface SearchEvent {
    data class OnSearchTextChanged(val query: String) : SearchEvent
}

sealed interface SearchNavigationEffect

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Success(val resultList: List<DataDao>) : SearchState
    data class Error(val message: String) : SearchState
}
