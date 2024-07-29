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
        val symbol = cryptoDetail.id

        DetailRoute(
            padding = padding,
            hazeState = hazeState,
            id = symbol
        )
    }
}

@Serializable
data class CryptoDetail(val id: String)