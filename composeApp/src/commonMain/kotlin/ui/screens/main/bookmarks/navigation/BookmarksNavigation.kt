package ui.screens.main.bookmarks.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.Bookmarks
import ui.navigation.util.News
import ui.navigation.util.NewsDetail
import ui.screens.main.bookmarks.BookmarksScreen

fun NavController.navigateToBookmarksScreen(navOptions: NavOptions) = navigate(Bookmarks, navOptions)

fun NavGraphBuilder.bookmarksScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavController
) {
    composable<Bookmarks> {
        BookmarksScreen(
            padding = padding,
            hazeState = hazeState,
            onBookmarkItemClick = { url -> navController.navigate(route = NewsDetail(url)) },
            onExploreNewsClick = {
                navController.navigate(News) {
                    popUpTo(navController.graph.startDestinationRoute!!)
                    launchSingleTop = true
                }
            }
        )
    }
}
