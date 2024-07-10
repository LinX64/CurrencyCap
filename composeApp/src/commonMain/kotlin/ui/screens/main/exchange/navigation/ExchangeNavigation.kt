package ui.screens.main.exchange.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.Screens.Exchange
import ui.navigation.util.NavRoutes
import ui.screens.main.exchange.ExchangeScreen

fun NavController.navigateToExchangeScreen(navOptions: NavOptions) = navigate(NavRoutes.EXCHANGE, navOptions)

fun NavGraphBuilder.exchangeScreen(
    padding: PaddingValues,
    onError: (String) -> Unit,
    hazeState: HazeState
) {
    composable<Exchange> {
        ExchangeScreen(
            padding = padding,
            onError = onError,
            hazeState = hazeState
        )
    }
}
