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
import kotlinx.coroutines.delay
import ui.components.ErrorView
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.overview.components.CryptoHorizontalItem
import ui.screens.main.search.SearchEvent.OnRetryClicked
import ui.screens.main.search.SearchEvent.OnSearchTextChanged
import ui.screens.main.search.SearchState.Error
import ui.screens.main.search.SearchState.Loading
import ui.screens.main.search.SearchState.Success
import ui.screens.main.search.components.EmptyView
import ui.screens.main.search.components.LeadingIcon
import ui.screens.main.search.components.SearchPlaceHolder
import ui.screens.main.search.components.TrailingIcon
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItem

@Composable
internal fun SearchRoute(
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState,
    onCryptoItemClick: (String) -> Unit
) {
    val state by searchViewModel.viewState.collectAsStateWithLifecycle()

    SearchScreen(
        state = state,
        padding = padding,
        hazeState = hazeState,
        onCryptoItemClick = onCryptoItemClick,
        handleEvent = { searchViewModel.handleEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    state: SearchState,
    onCryptoItemClick: (String) -> Unit,
    handleEvent: (SearchEvent) -> Unit
) {
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
                .padding(horizontal = SPACER_PADDING_16)
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
                    handleEvent(OnSearchTextChanged(it))
                },
                query = text,
                placeholder = { SearchPlaceHolder() },
                onSearch = { expanded = false },
                leadingIcon = { LeadingIcon() },
                trailingIcon = {
                    TrailingIcon(
                        expanded = expanded,
                        onCloseClick = {
                            expanded = false
                            text = ""
                            handleEvent(OnSearchTextChanged(""))
                        }
                    )
                }
            ) {
                BaseGlassLazyColumn(
                    modifier = Modifier.semantics { traversalIndex = 1f },
                    padding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
                    hazeState = hazeState
                ) {
                    searchResultContent(
                        state = state,
                        onRetryClicked = { handleEvent(OnRetryClicked(text)) },
                        onCryptoItemClick = onCryptoItemClick
                    )
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
    onRetryClicked: () -> Unit,
    onCryptoItemClick: (String) -> Unit
) = when (state) {
    is Success -> {
        val cryptoItems = state.cryptoList
        items(
            count = cryptoItems.size,
            key = { cryptoItems[it].id }
        ) {
            val data = cryptoItems[it]
            CryptoHorizontalItem(
                crypto = data,
                isLoading = false,
                onClick = onCryptoItemClick
            )
        }
    }

    is Error -> {
        item {
            ErrorView(
                message = state.message,
                onRetry = onRetryClicked
            )
        }
    }

    is Loading -> {
        items(5) {
            CryptoHorizontalItem(
                crypto = getDummyCryptoItem(),
                isLoading = true,
                onClick = { }
            )
        }
    }

    SearchState.Empty -> item { EmptyView() }
    else -> Unit
}
