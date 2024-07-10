package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import org.jetbrains.compose.resources.DrawableResource
import ui.navigation.util.Screens

sealed class BottomBarTab(
    val route: String,
    val icon: DrawableResource? = null,
) {
    data object Overview : BottomBarTab(
        route = Screens.Overview.route,
        icon = Res.drawable.ic_home
    )

    data object News : BottomBarTab(
        route = Screens.News.route,
        icon = Res.drawable.ic_news
    )

    data object Exchange : BottomBarTab(
        route = Screens.Exchange.route,
    )

    data object Bookmarks : BottomBarTab(
        route = Screens.Bookmarks.route,
        icon = Res.drawable.ic_save
    )

    data object Profile : BottomBarTab(
        route = Screens.Profile.route,
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

