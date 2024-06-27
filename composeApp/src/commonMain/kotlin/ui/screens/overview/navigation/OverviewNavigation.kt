package ui.screens.overview.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.NavRoutes
import ui.screens.overview.OverviewScreen
import ui.screens.search.navigation.navigateToSearchScreen

fun NavController.navigateToOverviewScreen() = navigate(NavRoutes.HOME)

fun NavGraphBuilder.homeScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable(NavRoutes.HOME) {
        OverviewScreen(
            padding = padding,
            hazeState = hazeState,
            onSearchCardClicked = { navController.navigateToSearchScreen() } // TODO: Add animation
        )
    }
}