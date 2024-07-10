package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import org.jetbrains.compose.resources.DrawableResource
import ui.navigation.util.ScreenRoutes

sealed class BottomBarTab(
    val route: String,
    val icon: DrawableResource? = null,
) {
    data object Overview : BottomBarTab(
        route = ScreenRoutes.OVERVIEW,
        icon = Res.drawable.ic_home
    )

    data object News : BottomBarTab(
        route = ScreenRoutes.NEWS,
        icon = Res.drawable.ic_news
    )

    data object Exchange : BottomBarTab(
        route = ScreenRoutes.EXCHANGE,
    )

    data object Bookmarks : BottomBarTab(
        route = ScreenRoutes.BOOKMARKS,
        icon = Res.drawable.ic_save
    )

    data object Profile : BottomBarTab(
        route = ScreenRoutes.PROFILE,
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

