package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun AppBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: String,
    selectedIndex: Int,
    onItemClicked: (Int) -> Unit,
    showNavigationBar: Boolean,
) {
    AnimatedVisibility(
        visible = showNavigationBar,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = modifier,
        ) {
            for (i in (0 until 3)) {
                NavigationBarItem(
                    selected = selectedIndex == i,
                    onClick = { onItemClicked(i) },
                    icon = {
                        Icon(
                            imageVector = when (i) {
                                0 -> Icons.Default.Home
                                1 -> Icons.Default.CurrencyExchange
                                else -> Icons.Default.Search
                            },
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                )
            }
        }
    }
}