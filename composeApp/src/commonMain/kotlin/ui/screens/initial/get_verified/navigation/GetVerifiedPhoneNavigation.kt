package ui.screens.initial.get_verified.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ui.screens.initial.get_verified.GetVerifiedPhoneScreen

fun NavController.navigateToGetVerifiedPhoneScreen() = navigate(GetVerifiedPhone)

fun NavGraphBuilder.getVerifiedPhoneScreen(
    onNavigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable<GetVerifiedPhone> {
        GetVerifiedPhoneScreen(
            navigateToMarketOverview = onNavigateToMarketOverview,
            onError = onError
        )
    }
}

@Serializable
data object GetVerifiedPhone