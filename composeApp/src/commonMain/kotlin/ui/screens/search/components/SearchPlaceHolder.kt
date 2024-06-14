package ui.screens.search.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SearchPlaceHolder() {
    Text(
        text = "Search for currency or coin",
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
    )
}