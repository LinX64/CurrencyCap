package ui.screens.search

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.screens.search.SearchEvent.OnSearchCleared
import ui.screens.search.SearchEvent.OnSearchClicked
import ui.screens.search.SearchEvent.OnSearchCloseClicked
import ui.screens.search.SearchEvent.OnSearchResultClicked
import ui.screens.search.SearchEvent.OnSearchTextChanged

class SearchViewModel(
    private val mainRepository: MainRepository
) : MviViewModel<SearchEvent, SearchState, SearchNavigationEffect>(SearchState.Idle) {

    private val searchQuery: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is OnSearchTextChanged -> search(event.query)
            is OnSearchClicked -> search(searchQuery.value ?: "")
            is OnSearchResultClicked -> TODO()
            OnSearchCloseClicked -> TODO()
            OnSearchCleared -> TODO()
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun search(query: String) {
        with(searchQuery) {
            value = query.trim()
            debounce(500)
                .filterNotNull()
                .distinctUntilChanged()
                .flatMapLatest { flowOf(getCoinCapRates()) }
                .launchIn(viewModelScope)
        }
    }

    // TODO: Maybe try to implement this in a different way
    private fun getCoinCapRates() {
        mainRepository.getCoinCapRates()
            .asResult()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> setState { SearchState.Success(result.data) }
                    is NetworkResult.Error -> setState { SearchState.Error(result.exception.message.toString()) }
                    NetworkResult.Loading -> setState { SearchState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }
}