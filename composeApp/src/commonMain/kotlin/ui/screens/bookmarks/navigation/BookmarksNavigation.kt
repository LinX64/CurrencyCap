package ui.screens.bookmarks.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.NavRoutes
import ui.screens.ai_predict.BookmarksScreen

fun NavController.navigateToBookmarksScreen() = navigate(NavRoutes.BOOKMARKS)

fun NavGraphBuilder.bookmarksScreen(padding: PaddingValues, hazeState: HazeState) {
    composable(NavRoutes.BOOKMARKS) {
        BookmarksScreen(padding = padding, hazeState = hazeState)
    }
}
