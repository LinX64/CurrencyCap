package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_bitcoin
import currencycap.composeapp.generated.resources.ic_chart
import currencycap.composeapp.generated.resources.ic_news
import org.jetbrains.compose.resources.DrawableResource

sealed class VerticalBarTab(
    val title: String,
    val icon: DrawableResource,
) {
    data object NEWS : VerticalBarTab(
        title = "News",
        icon = Res.drawable.ic_news
    )

    data object CRYPTO : VerticalBarTab(
        title = "Crypto",
        icon = Res.drawable.ic_bitcoin
    )

    data object STOCK : VerticalBarTab(
        title = "Stock",
        icon = Res.drawable.ic_chart
    )
}

internal val myTabs = listOf(
    VerticalBarTab.NEWS,
    VerticalBarTab.CRYPTO,
    VerticalBarTab.STOCK
)