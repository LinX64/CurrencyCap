import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.RateDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

private const val SEARCH_QUERY = "searchQuery"

class SearchViewModel(
    // private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val searchQuery = flowOf("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResultState: StateFlow<SearchUiState> = searchQuery
        .debounce(1000L)
        .distinctUntilChanged()
        .filter { it.length >= 2 }
        .flatMapLatest {
            if (it.isEmpty()) flowOf(SearchUiState.EmptyQuery) else makeTheCall(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SearchUiState.EmptyQuery
        )

    private fun makeTheCall(it: String): Flow<SearchUiState.EmptyQuery> {
        return flowOf(SearchUiState.EmptyQuery)
    }

    fun onSearchClick(myQuery: String) {
        // savedStateHandle[SEARCH_QUERY] = myQuery
    }

    fun onClear() {
        //savedStateHandle[SEARCH_QUERY] = ""
    }
}

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data object EmptyQuery : SearchUiState
    data object EmptyResponse : SearchUiState
    data object SearchNotReady : SearchUiState
    data class Success(val users: List<RateDao>) : SearchUiState
    data class Error(val error: String) : SearchUiState
}

