package ui.screens.exchange.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.NavRoutes
import ui.screens.exchange.ExchangeScreen

fun NavController.navigateToExchangeScreen() = navigate(NavRoutes.EXCHANGE)

fun NavGraphBuilder.exchangeScreen(
    padding: PaddingValues,
    onError: (String) -> Unit,
    hazeState: HazeState
) {
    composable(NavRoutes.EXCHANGE) {
        ExchangeScreen(
            padding = padding,
            onError = onError,
            hazeState = hazeState
        )
    }
}
