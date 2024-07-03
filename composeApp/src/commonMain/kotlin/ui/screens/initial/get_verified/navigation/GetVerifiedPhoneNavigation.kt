package ui.screens.initial.get_verified.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ui.navigation.util.NavRoutes
import ui.navigation.util.PHONE_NUMBER
import ui.screens.initial.get_verified.GetVerifiedPhoneScreen

fun NavController.navigateToGetVerifiedPhoneScreen() = navigate(NavRoutes.GET_VERIFIED_PHONE)

fun NavGraphBuilder.getVerifiedPhoneScreen(
    padding: PaddingValues,
    onNavigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable(
        route = NavRoutes.GET_VERIFIED_PHONE,
        arguments = listOf(
            navArgument(PHONE_NUMBER) {
                nullable = false
                type = NavType.StringType
            }
        )
    ) {
        GetVerifiedPhoneScreen(
            padding = padding,
            navigateToMarketOverview = onNavigateToMarketOverview,
            onError = onError
        )
    }
}
