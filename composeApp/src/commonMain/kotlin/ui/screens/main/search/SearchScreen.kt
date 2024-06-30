package ui.screens.main.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import domain.model.main.Rate
import kotlinx.coroutines.delay
import ui.components.ErrorView
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.search.components.EmptyView
import ui.screens.main.search.components.LeadingIcon
import ui.screens.main.search.components.SearchItem
import ui.screens.main.search.components.SearchPlaceHolder
import ui.screens.main.search.components.TrailingIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState
) {
    val state by searchViewModel.viewState.collectAsStateWithLifecycle()
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(true) }
    val showKeyboard = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .semantics { isTraversalGroup = true }
        ) {
            SearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = 0f }
                    .focusRequester(focusRequester),
                active = expanded,
                onActiveChange = { expanded = it },
                onQueryChange = {
                    text = it
                    searchViewModel.handleEvent(SearchEvent.OnSearchTextChanged(it))
                },
                query = text,
                placeholder = { SearchPlaceHolder() },
                onSearch = { expanded = false },
                leadingIcon = { LeadingIcon() },
                trailingIcon = {
                    TrailingIcon(expanded = expanded, onCloseClick = {
                        expanded = false
                        text = ""
                    })
                }
            ) {
                BaseGlassLazyColumn(
                    modifier = Modifier.semantics { traversalIndex = 1f },
                    padding = padding,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    hazeState = hazeState
                ) {
                    searchResultContent(state, onRetryClicked = { searchViewModel.handleEvent(SearchEvent.OnRetryClicked(text)) })
                }
            }

            SearchTabs()
        }
    }

    LaunchedEffect(focusRequester) {
        if (showKeyboard.equals(true)) {
            focusRequester.requestFocus()
            delay(100)
            keyboard?.show()
        }
    }
}

@Composable
private fun SearchTabs() {
    // TODO: Show different tabs here

}

private fun LazyListScope.searchResultContent(
    state: SearchState,
    onRetryClicked: () -> Unit
) = when (state) {
    is SearchState.Loading -> {
        items(5) {
            SearchItem(
                rate = Rate(
                    symbol = "USD",
                    rateUsd = "1.0",
                    currencySymbol = "USD",
                    id = "USD",
                    type = "USD"
                ),
                isLoading = true
            )
        }
    }

    is SearchState.Success -> {
        val result = state.resultList
        items(result.size) {
            val data = result[it]
            SearchItem(rate = data)
        }
    }

    is SearchState.Error -> {
        item {
            ErrorView(
                message = state.message,
                onRetry = onRetryClicked
            )
        }
    }

    SearchState.Empty -> item { EmptyView() }

    else -> Unit
}
