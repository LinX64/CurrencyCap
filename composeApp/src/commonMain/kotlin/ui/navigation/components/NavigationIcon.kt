package ui.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun NavigationIcon(i: Int) {
    Icon(
        imageVector = when (i) {
            0 -> Icons.Default.Home
            1 -> Icons.Default.CurrencyExchange
            else -> Icons.Default.Search
        },
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface,
    )
}