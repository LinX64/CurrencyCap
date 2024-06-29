package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import org.jetbrains.compose.resources.DrawableResource
import ui.navigation.util.NavRoutes

sealed class BottomBarTab(
    val route: String,
    val icon: DrawableResource? = null,
) {
    data object Overview : BottomBarTab(
        route = NavRoutes.OVERVIEW,
        icon = Res.drawable.ic_home
    )

    data object News : BottomBarTab(
        route = NavRoutes.NEWS,
        icon = Res.drawable.ic_news
    )

    data object Exchange : BottomBarTab(
        route = NavRoutes.EXCHANGE,
    )

    data object Bookmarks : BottomBarTab(
        route = NavRoutes.BOOKMARKS,
        icon = Res.drawable.ic_save
    )

    data object Profile : BottomBarTab(
        route = "Profile",
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

