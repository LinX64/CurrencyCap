package ui.screens.auth.fill_profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.navigation.NavRoutes
import ui.screens.auth.fill_profile.FillProfileScreen

fun NavController.navigateToFillProfileScreen() = navigate(NavRoutes.FILL_PROFILE)

fun NavGraphBuilder.fillProfileScreen(
    padding: PaddingValues,
    onNavigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.FILL_PROFILE) {
        FillProfileScreen(
            padding = padding,
            navigateToMarketOverview = onNavigateToMarketOverview,
            onError = onError
        )
    }
}
