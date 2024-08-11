package ui.screens.initial.fill_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ui.screens.initial.fill_profile.FillProfileScreen

fun NavController.navigateToFillProfileScreen() = navigate(FillProfile)

fun NavGraphBuilder.fillProfileScreen(
    onNavigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable<FillProfile> {
        FillProfileScreen(
            onError = onError,
            navigateToMarketOverview = onNavigateToMarketOverview,
        )
    }
}

@Serializable
data object FillProfile