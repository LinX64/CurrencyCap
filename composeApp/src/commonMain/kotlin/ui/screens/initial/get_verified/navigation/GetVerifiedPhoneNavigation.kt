package ui.screens.initial.get_verified.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.navigation.util.NavRoutes
import ui.screens.initial.get_verified.GetVerifiedPhoneScreen

fun NavController.navigateToGetVerifiedPhoneScreen() = navigate(NavRoutes.GET_VERIFIED_PHONE)

fun NavGraphBuilder.getVerifiedPhoneScreen(
    padding: PaddingValues,
    onNavigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.GET_VERIFIED_PHONE) {
        GetVerifiedPhoneScreen(
            padding = padding,
            navigateToMarketOverview = onNavigateToMarketOverview,
            onError = onError
        )
    }
}
