package ui.navigation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun NavigationText(i: Int) {
    Text(
        text = when (i) {
            0 -> "Home"
            1 -> "Exchange"
            else -> "Search"
        },
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
    )
}