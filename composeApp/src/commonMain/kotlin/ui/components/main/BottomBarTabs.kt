package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import org.jetbrains.compose.resources.DrawableResource
import ui.navigation.util.Screens

sealed class BottomBarTab(
    val screen: Screens,
    val icon: DrawableResource? = null,
) {
    data object Overview : BottomBarTab(
        screen = Screens.Overview,
        icon = Res.drawable.ic_home
    )

    data object News : BottomBarTab(
        screen = Screens.News,
        icon = Res.drawable.ic_news
    )

    data object Exchange : BottomBarTab(
        screen = Screens.Exchange,
    )

    data object Bookmarks : BottomBarTab(
        screen = Screens.Bookmarks,
        icon = Res.drawable.ic_save
    )

    data object Profile : BottomBarTab(
        screen = Screens.Profile,
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

