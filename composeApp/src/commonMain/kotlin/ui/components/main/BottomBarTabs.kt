package ui.components.main

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.bookmarks
import currencycap.composeapp.generated.resources.exchange
import currencycap.composeapp.generated.resources.ic_home
import currencycap.composeapp.generated.resources.ic_news
import currencycap.composeapp.generated.resources.ic_save
import currencycap.composeapp.generated.resources.ic_user_normal
import currencycap.composeapp.generated.resources.news
import currencycap.composeapp.generated.resources.overview
import currencycap.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class BottomBarTab(
    val route: StringResource,
    val icon: DrawableResource? = null
) {
    Overview(Res.string.overview, Res.drawable.ic_home),
    News(Res.string.news, Res.drawable.ic_news),
    Exchange(Res.string.exchange),
    Bookmarks(Res.string.bookmarks, Res.drawable.ic_save),
    Profile(Res.string.profile, Res.drawable.ic_user_normal)
}

internal val tabs = listOf(
    BottomBarTab.Overview,
    BottomBarTab.News,
    BottomBarTab.Exchange,
    BottomBarTab.Bookmarks,
    BottomBarTab.Profile
)

