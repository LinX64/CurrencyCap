package ui.screens.search

import domain.model.DataDao

sealed interface SearchEvent {
    data class OnSearchTextChanged(val query: String) : SearchEvent
    data class OnSearchClicked(val query: String) : SearchEvent
    data class OnSearchResultClicked(val dataDao: DataDao) : SearchEvent
    data object OnSearchCleared : SearchEvent
    data object OnSearchCloseClicked : SearchEvent
}

sealed interface SearchNavigationEffect

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Success(val resultList: List<DataDao>) : SearchState
    data class Error(val message: String) : SearchState
}
