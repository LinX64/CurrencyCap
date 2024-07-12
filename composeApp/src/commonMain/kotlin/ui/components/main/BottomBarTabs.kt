package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import org.jetbrains.compose.resources.DrawableResource
import ui.navigation.util.ScreenRoutes

enum class BottomBarTab(
    val screen: String,
    val icon: DrawableResource? = null
) {
    Overview(ScreenRoutes.OVERVIEW, Res.drawable.ic_home),
    News(ScreenRoutes.NEWS, Res.drawable.ic_news),
    Exchange(ScreenRoutes.EXCHANGE),
    Bookmarks(ScreenRoutes.BOOKMARKS, Res.drawable.ic_save),
    Profile(ScreenRoutes.PROFILE, Res.drawable.ic_user_normal)
}

val tabs = listOf(
    BottomBarTab.Overview,
    BottomBarTab.News,
    BottomBarTab.Exchange,
    BottomBarTab.Bookmarks,
    BottomBarTab.Profile
)

