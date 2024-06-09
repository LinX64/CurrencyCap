package ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_close_24
import currencycap.composeapp.generated.resources.baseline_search_24
import di.koinViewModel
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>(),
    padding: PaddingValues
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(padding),
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
                onQueryChange = { text = it },
                query = text,
                placeholder = { SearchPlaceHolder() },
                onSearch = { expanded = false },
                leadingIcon = { LeadingIcon() },
                trailingIcon = {
                    TrailingIcon(expanded = expanded, onCloseClick = { expanded = false })
                }
            ) {
                HandleList()
            }
        }
    }
}

@Composable
private fun TrailingIcon(
    expanded: Boolean,
    onCloseClick: () -> Unit
) {
    if (expanded) {
        IconButton(
            onClick = onCloseClick,
            modifier = Modifier.semantics { traversalIndex = 2f }
        ) {
            Icon(
                painter = painterResource(Res.drawable.baseline_close_24),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun HandleList() {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.semantics { traversalIndex = 1f },
    ) {
        val list = List(100) { "Text $it" }
        items(count = list.size) {
            Text(
                text = list[it],
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun LeadingIcon() {
    Icon(
        painter = painterResource(Res.drawable.baseline_search_24),
        contentDescription = null
    )
}

@Composable
private fun SearchPlaceHolder() {
    Text(
        text = "Search for currency or coin",
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    )
}

