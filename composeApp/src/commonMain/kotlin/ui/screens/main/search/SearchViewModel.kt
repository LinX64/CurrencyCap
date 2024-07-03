package ui.screens.main.search

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ui.common.MviViewModel
import ui.screens.main.search.SearchEvent.OnRetryClicked
import ui.screens.main.search.SearchEvent.OnSearchClicked
import ui.screens.main.search.SearchEvent.OnSearchResultClicked
import ui.screens.main.search.SearchEvent.OnSearchTextChanged

class SearchViewModel(
    private val mainRepository: MainRepository
) : MviViewModel<SearchEvent, SearchState, SearchNavigationEffect>(SearchState.Idle) {

    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is OnSearchTextChanged -> search(event.query)
            is OnSearchClicked -> search(searchQuery.value)
            is OnSearchResultClicked -> TODO()
            is OnRetryClicked -> search(event.query)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun search(query: String) {
        with(searchQuery) {
            if (query.isEmpty()) {
                setState { SearchState.Idle }
                return
            }

            value = query.trim()
            debounce(500)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest(mainRepository::search)
                .asResult()
                .onEach { result ->
                    setState {
                        when (result) {
                            is NetworkResult.Error -> SearchState.Error(result.throwable.message ?: "")
                            is NetworkResult.Loading -> SearchState.Loading
                            is NetworkResult.Success -> {
                                if (result.data.isNotEmpty()) {
                                    SearchState.Success(result.data)
                                } else SearchState.Empty
                            }
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}