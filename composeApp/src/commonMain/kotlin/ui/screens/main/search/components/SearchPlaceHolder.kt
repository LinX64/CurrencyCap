package ui.screens.main.search.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ui.components.base.BaseCenterColumn

@Composable
internal fun SearchPlaceHolder() {
    BaseCenterColumn {
        Text(
            text = "Search for currency or coin",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
    }
}