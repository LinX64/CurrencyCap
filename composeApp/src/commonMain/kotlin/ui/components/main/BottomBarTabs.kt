package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import kotlinx.collections.immutable.persistentSetOf
import org.jetbrains.compose.resources.DrawableResource
import ui.navigation.util.ScreenRoutes

enum class BottomBarTab(
    val route: String,
    val icon: DrawableResource? = null
) {
    OVERVIEW(ScreenRoutes.OVERVIEW, Res.drawable.ic_home),
    NEWS(ScreenRoutes.NEWS, Res.drawable.ic_news),
    EXCHANGE(ScreenRoutes.EXCHANGE),
    BOOKMARKS(ScreenRoutes.BOOKMARKS, Res.drawable.ic_save),
    PROFILE(ScreenRoutes.PROFILE, Res.drawable.ic_user_normal)
}

internal val tabs = persistentSetOf(
    BottomBarTab.OVERVIEW,
    BottomBarTab.NEWS,
    BottomBarTab.EXCHANGE,
    BottomBarTab.BOOKMARKS,
    BottomBarTab.PROFILE
)

