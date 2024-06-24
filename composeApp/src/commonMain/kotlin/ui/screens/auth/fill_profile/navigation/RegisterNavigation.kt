package ui.screens.auth.fill_profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.auth.fill_profile.FillProfileScreen
import ui.screens.overview.navigation.navigateToOverviewScreen

fun NavController.navigateToFillProfileScreen() = navigate(NavRoutes.FILL_PROFILE)

fun NavGraphBuilder.fillProfileScreen(
    navController: NavHostController,
    padding: PaddingValues,
    onError: (message: String) -> Unit
) {
    composable(NavRoutes.FILL_PROFILE) {
        BlurBackground {
            FillProfileScreen(
                padding = padding,
                navigateToMarketOverview = { navController.navigateToOverviewScreen() },
                onError = onError
            )
        }
    }
}
