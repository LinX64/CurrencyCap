package ui.screens.initial.fill_profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ui.screens.initial.fill_profile.FillProfileScreen

fun NavController.navigateToFillProfileScreen() = navigate(FillProfile)

fun NavGraphBuilder.fillProfileScreen(
    padding: PaddingValues,
    onNavigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    composable<FillProfile> {
        FillProfileScreen(
            padding = padding,
            onError = onError,
            navigateToMarketOverview = onNavigateToMarketOverview,
        )
    }
}

@Serializable
data object FillProfile