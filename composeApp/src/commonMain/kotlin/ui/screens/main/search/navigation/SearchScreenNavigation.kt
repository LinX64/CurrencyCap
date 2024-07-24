package ui.screens.main.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.navigation.util.Screen.CryptoDetail
import ui.screens.main.search.SearchRoute

fun NavController.navigateToSearchScreen() = navigate(Explore)

fun NavGraphBuilder.searchScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable<Explore> {
        SearchRoute(
            padding = padding,
            hazeState = hazeState,
            onCryptoItemClick = { symbol ->
                navController.navigate(route = CryptoDetail(symbol))
            }
        )
    }
}

@Serializable
data object Explore