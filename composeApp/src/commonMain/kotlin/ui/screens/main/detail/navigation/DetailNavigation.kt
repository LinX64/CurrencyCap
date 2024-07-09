package ui.screens.main.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.NavRoutes
import ui.navigation.util.SYMBOL
import ui.screens.main.detail.DetailRoute

fun NavController.navigateToDetailScreen(navOptions: NavOptions) = navigate(NavRoutes.CRYPTO_DETAIL, navOptions)

fun NavGraphBuilder.detailScreen(
    padding: PaddingValues,
    onError: (String) -> Unit,
    hazeState: HazeState
) {
    composable(
        route = NavRoutes.CRYPTO_DETAIL + "/{$SYMBOL}",
        arguments = listOf(
            navArgument(SYMBOL) {
                nullable = false
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val symbol = backStackEntry.arguments?.getString(SYMBOL) ?: ""

        DetailRoute(
            padding = padding,
            hazeState = hazeState,
            symbol = symbol,
        )
    }
}
