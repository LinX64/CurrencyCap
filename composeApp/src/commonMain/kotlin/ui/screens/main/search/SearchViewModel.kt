package ui.screens.main.search

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.repository.ExploreRepository
import kotlinx.collections.immutable.toImmutableList
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
import ui.screens.main.search.SearchEvent.OnSearchTextChanged
import ui.screens.main.search.SearchState.Empty
import ui.screens.main.search.SearchState.Error
import ui.screens.main.search.SearchState.Idle
import ui.screens.main.search.SearchState.Loading
import ui.screens.main.search.SearchState.Success

class SearchViewModel(
    private val exploreRepository: ExploreRepository
) : MviViewModel<SearchEvent, SearchState, SearchNavigationEffect>(Idle) {

    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is OnSearchTextChanged -> search(event.query)
            is OnSearchClicked -> search(searchQuery.value)
            is OnRetryClicked -> search(event.query)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun search(query: String) {
        with(searchQuery) {
            if (query.isEmpty()) {
                setState { Idle }
                return
            }

            value = query.trim()
            debounce(500)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest(exploreRepository::search)
                .asResult()
                .onEach { result ->
                    setState {
                        when (result) {
                            is NetworkResult.Error -> Error(result.throwable.message ?: "")
                            is NetworkResult.Loading -> Loading
                            is NetworkResult.Success -> {
                                if (result.data.isNotEmpty()) {
                                    Success(result.data.toImmutableList())
                                } else Empty
                            }
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}