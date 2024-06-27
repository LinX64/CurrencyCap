package ui.components.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.ui.graphics.vector.ImageVector

sealed class VerticalBarTab(
    val title: String,
    val icon: ImageVector,
) {
    data object STOCK : VerticalBarTab(
        title = "Stock",
        icon = Icons.Default.Home
    )

    data object CRYPTO : VerticalBarTab(
        title = "Crypto",
        icon = Icons.Default.Newspaper
    )

    data object NEWS : VerticalBarTab(
        title = "News",
        icon = Icons.Default.Home
    )
}

internal val myTabs = listOf(
    VerticalBarTab.STOCK,
    VerticalBarTab.CRYPTO,
    VerticalBarTab.NEWS
)