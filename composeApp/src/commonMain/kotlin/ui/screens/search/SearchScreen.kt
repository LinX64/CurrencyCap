package ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import di.koinViewModel
import domain.model.DataDao
import ui.components.ErrorView
import ui.screens.search.components.EmptyView
import ui.screens.search.components.LeadingIcon
import ui.screens.search.components.SearchItem
import ui.screens.search.components.SearchPlaceHolder
import ui.screens.search.components.TrailingIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>(),
    padding: PaddingValues
) {
    val state by searchViewModel.viewState.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

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
                    .semantics { traversalIndex = 0f },
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
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.semantics { traversalIndex = 1f }
                ) {
                    searchResultContent(state, onRetryClicked = { searchViewModel.handleEvent(SearchEvent.OnRetryClicked(text)) })
                }
            }
        }
    }
}

private fun LazyListScope.searchResultContent(
    state: SearchState,
    onRetryClicked: () -> Unit
) = when (state) {
    is SearchState.Loading -> {
        items(5) {
            SearchItem(
                rate = DataDao(
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
                onRetryClicked = onRetryClicked
            )
        }
    }

    SearchState.Empty -> {
        item {
            EmptyView()
        }
    }

    else -> Unit
}
