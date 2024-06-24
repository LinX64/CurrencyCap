package ui.screens.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.search.SearchScreen

fun NavController.navigateToSearchScreen() = navigate(NavRoutes.EXPLORE)

fun NavGraphBuilder.searchScreen(padding: PaddingValues) {
    composable(NavRoutes.EXPLORE) {
        BlurBackground {
            SearchScreen(padding = padding)
        }
    }
}
