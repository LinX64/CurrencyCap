package ui.screens.search

import domain.model.DataDao
import domain.model.RateDto

sealed interface SearchEvent {
    data class OnSearchTextChanged(val query: String) : SearchEvent
    data class OnSearchClicked(val query: String) : SearchEvent
    data class OnSearchResultClicked(val dataDao: DataDao) : SearchEvent
    data class OnRetryClicked(val query: String) : SearchEvent
}

sealed interface SearchNavigationEffect

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data object Empty : SearchState
    data class Success(val resultList: List<RateDto>) : SearchState
    data class Error(val message: String) : SearchState
}
