package ui.screens.main.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.detail.DetailRoute

fun NavGraphBuilder.detailScreen(
    padding: PaddingValues,
    hazeState: HazeState
) {
    composable<CryptoDetail> { backStackEntry ->
        val cryptoDetail: CryptoDetail = backStackEntry.toRoute()
        val id = cryptoDetail.id
        val symbol = cryptoDetail.symbol

        DetailRoute(
            padding = padding,
            hazeState = hazeState,
            id = id,
            symbol = symbol
        )
    }
}

@Serializable
data class CryptoDetail(val id: String, val symbol: String)