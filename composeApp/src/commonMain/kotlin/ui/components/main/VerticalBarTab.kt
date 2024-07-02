package ui.components.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.ui.graphics.vector.ImageVector

sealed class VerticalBarTab(
    val title: String,
    val icon: ImageVector,
) {
    data object STOCK : VerticalBarTab(
        title = "Stock",
        icon = Icons.Filled.Accessibility//Res.drawable.ic_chart
    )

    data object CRYPTO : VerticalBarTab(
        title = "Crypto",
        icon = Icons.Filled.Accessibility//Res.drawable.ic_bitcoin
    )

    data object NEWS : VerticalBarTab(
        title = "News",
        icon = Icons.Filled.Accessibility//Res.drawable.ic_news
    )
}

internal val myTabs = listOf(
    VerticalBarTab.STOCK,
    VerticalBarTab.CRYPTO,
    VerticalBarTab.NEWS
)