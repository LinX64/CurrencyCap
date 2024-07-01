package ui.screens.initial.fill_profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.navigation.util.NavRoutes
import ui.screens.initial.fill_profile.FillProfileScreen

fun NavController.navigateToFillProfileScreen() = navigate(NavRoutes.FILL_PROFILE)

fun NavGraphBuilder.fillProfileScreen(
    padding: PaddingValues,
    navController: NavHostController,
    onNavigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.FILL_PROFILE) {
        FillProfileScreen(
            padding = padding,
            onError = onError,
            navigateToGetVerificationCode = { phoneNumber -> navController.navigate(NavRoutes.GET_VERIFIED_PHONE + phoneNumber) },
            navigateToMarketOverview = onNavigateToMarketOverview,
        )
    }
}
