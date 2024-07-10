package ui.screens.main.bookmarks.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.Screens.Bookmarks
import ui.navigation.util.NavRoutes
import ui.screens.main.bookmarks.BookmarksScreen

fun NavController.navigateToBookmarksScreen(navOptions: NavOptions) = navigate(NavRoutes.BOOKMARKS, navOptions)

fun NavGraphBuilder.bookmarksScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavController
) {
    composable<Bookmarks> {
        BookmarksScreen(
            padding = padding,
            hazeState = hazeState,
            onBookmarkItemClick = { url -> navController.navigate(NavRoutes.NEWS_DETAIL + "/$url") },
            onExploreNewsClick = {
                navController.navigate(NavRoutes.NEWS) {
                    popUpTo(navController.graph.startDestinationRoute!!)
                    launchSingleTop = true
                }
            }
        )
    }
}
