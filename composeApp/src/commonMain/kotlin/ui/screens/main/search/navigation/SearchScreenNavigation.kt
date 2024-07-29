package ui.screens.main.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.detail.navigation.CryptoDetail
import ui.screens.main.search.SearchRoute

fun NavController.navigateToSearchScreen() = navigate(Explore)

fun NavGraphBuilder.searchScreen(
    hazeState: HazeState,
    navController: NavHostController
) {
    composable<Explore> {
        SearchRoute(
            hazeState = hazeState,
            onCryptoItemClick = { id, symbol ->
                navController.navigate(route = CryptoDetail(id, symbol))
            }
        )
    }
}

@Serializable
data object Explore