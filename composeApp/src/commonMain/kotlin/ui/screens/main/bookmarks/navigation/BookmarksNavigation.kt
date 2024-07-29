package ui.screens.main.bookmarks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.bookmarks.BookmarksRoute
import ui.screens.main.news.news_detail.navigation.NewsDetail

fun NavController.navigateToBookmarksScreen(navOptions: NavOptions) = navigate(Bookmarks, navOptions)

fun NavGraphBuilder.bookmarksScreen(
    hazeState: HazeState,
    navController: NavController,
    onExploreNewsClick: () -> Unit
) {
    composable<Bookmarks> {
        BookmarksRoute(
            hazeState = hazeState,
            onBookmarkItemClick = { url -> navController.navigate(NewsDetail(url)) },
            onExploreNewsClick = onExploreNewsClick
        )
    }
}

@Serializable
data object Bookmarks