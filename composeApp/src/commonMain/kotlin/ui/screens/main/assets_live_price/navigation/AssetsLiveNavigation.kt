package ui.screens.main.assets_live_price.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ui.screens.main.assets_live_price.AssetsLivePriceScreen

internal fun NavGraphBuilder.assetsLiveScreen(
    onError: (message: String) -> Unit,

    ) {
    composable<LivePrices> {
        AssetsLivePriceScreen()
    }
}

@Serializable
data object LivePrices
