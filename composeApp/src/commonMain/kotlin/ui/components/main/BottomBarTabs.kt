package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import org.jetbrains.compose.resources.DrawableResource
import ui.navigation.NavRoutes

sealed class BottomBarTab(
    val title: String,
    val icon: DrawableResource? = null,
) {
    data object Overview : BottomBarTab(
        title = NavRoutes.HOME,
        icon = Res.drawable.ic_home
    )

    data object News : BottomBarTab(
        title = NavRoutes.NEWS,
        icon = Res.drawable.ic_news
    )

    data object Exchange : BottomBarTab(
        title = NavRoutes.EXCHANGE,
    )

    data object Bookmarks : BottomBarTab(
        title = NavRoutes.BOOKMARKS,
        icon = Res.drawable.ic_save
    )

    data object Profile : BottomBarTab(
        title = "Profile",
        icon = Res.drawable.ic_user_normal
    )
}

val tabs = listOf(
    BottomBarTab.Overview,
    BottomBarTab.News,
    BottomBarTab.Exchange,
    BottomBarTab.Bookmarks,
    BottomBarTab.Profile
)

