package ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.client.currencycap.ui.common.getSearchText
import ui.screens.SearchUiState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBarView(
    modifier: Modifier = Modifier,
    searchUiState: SearchUiState,
    isSearchBarActive: (Boolean) -> Unit = {},
    onSearchClick: (String) -> Unit,
    onItemClick: (String) -> Unit,
    onClear: () -> Unit
) {
    val query = remember { mutableStateOf("") }
    val isQueryEmpty = query.value.isEmpty()
    var active by rememberSaveable { mutableStateOf(false) }
    val showShouldLoading = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics { contentDescription = "SearchScreen"; isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            query = query.value,
            onQueryChange = {
                query.value = it
                onSearchClick(it)
            },
            onSearch = onSearchClick,
            placeholder = { Text(text = getSearchText()) },
            leadingIcon = { LeadingIcon() },
            trailingIcon = {
                if (isQueryEmpty.not()) {
                    TrailingIcon(onClear = {
                        active = false
                        query.value = ""
                        onClear()
                    })
                }
            },
            active = active.also(isSearchBarActive),
            onActiveChange = { active = it },
            colors = SearchBarDefaults.colors(
                dividerColor = Color.Transparent
            )
        ) {
            LazyVerticalGrid(
                modifier = modifier.fillMaxWidth(),
                columns = GridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                onboardingView(searchUiState = searchUiState, onItemClick = onItemClick)
            }

            if (showShouldLoading.value) {
//                LoadingDots()
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        if (searchUiState is SearchUiState.Loading) {
            showShouldLoading.value = true
        }

        if (searchUiState !is SearchUiState.Loading) {
            showShouldLoading.value = false
        }
    }
}

private fun LazyGridScope.onboardingView(
    searchUiState: SearchUiState,
    onItemClick: (String) -> Unit
) = when (searchUiState) {
    is SearchUiState.Loading,
    is SearchUiState.EmptyQuery,
    is SearchUiState.SearchNotReady -> Unit

    is SearchUiState.Success -> {
        val users = searchUiState.users
        items(users.size) { index ->
//            UserItem(user = users[index], onItemClick = { onItemClick(users[index].login) })
        }
    }

    is SearchUiState.EmptyResponse -> {
        item {
//            EmptyResponseView()
        }
    }

    is SearchUiState.Error -> {
        item {
//            ErrorView()
        }
    }
}

@Composable
private fun LeadingIcon() {
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = null,
        tint = Color.Gray
    )
}

@Composable
private fun TrailingIcon(onClear: () -> Unit) {
    IconButton(
        onClick = { onClear() }
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null
        )
    }
}