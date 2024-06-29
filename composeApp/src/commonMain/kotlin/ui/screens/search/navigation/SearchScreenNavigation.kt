package ui.screens.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.NavRoutes
import ui.screens.search.SearchScreen

fun NavController.navigateToSearchScreen() = navigate(NavRoutes.EXPLORE)

fun NavGraphBuilder.searchScreen(padding: PaddingValues, hazeState: HazeState) {
    composable(NavRoutes.EXPLORE) {
        SearchScreen(padding = padding, hazeState = hazeState)
    }
}
