package ui.screens.main.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.NavRoutes
import ui.navigation.util.Screens.Explore
import ui.screens.main.search.SearchScreen

fun NavController.navigateToSearchScreen() = navigate(Explore)

fun NavGraphBuilder.searchScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable<Explore> {
        SearchScreen(
            padding = padding,
            hazeState = hazeState,
            onCryptoItemClick = { symbol ->
                navController.navigate(NavRoutes.CRYPTO_DETAIL + "/$symbol")
            }
        )
    }
}
