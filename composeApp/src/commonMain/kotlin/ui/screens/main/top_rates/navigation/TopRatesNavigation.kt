package ui.screens.main.top_rates.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.top_rates.TopRatesRoute

fun NavGraphBuilder.topRatesScreen(
    hazeState: HazeState,
) {
    composable<TopRates> {
        TopRatesRoute()
    }
}

@Serializable
data object TopRates