package ui.screens.main.exchange.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.exchange.ExchangeRoute

fun NavController.navigateToExchangeScreen(navOptions: NavOptions) = navigate(Exchange, navOptions)

fun NavGraphBuilder.exchangeScreen(
    onError: (String) -> Unit,
    hazeState: HazeState
) {
    composable<Exchange> {
        ExchangeRoute(
            hazeState = hazeState,
            onError = onError,
        )
    }
}

@Serializable
data object Exchange