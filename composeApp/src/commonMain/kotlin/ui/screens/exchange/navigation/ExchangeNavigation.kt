package ui.screens.exchange.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.exchange.ExchangeScreen

fun NavController.navigateToExchangeScreen() = navigate(NavRoutes.CURRENCY_CONVERTER)

fun NavGraphBuilder.exchangeScreen(padding: PaddingValues) {
    composable(NavRoutes.CURRENCY_CONVERTER) {
        BlurBackground {
            ExchangeScreen(padding = padding)
        }
    }
}
