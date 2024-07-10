package ui.screens.main.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.chrisbanes.haze.HazeState
import ui.navigation.Screens.CryptoDetail
import ui.navigation.util.NavRoutes
import ui.screens.main.detail.DetailRoute

fun NavController.navigateToDetailScreen(navOptions: NavOptions) = navigate(NavRoutes.CRYPTO_DETAIL, navOptions)

fun NavGraphBuilder.detailScreen(
    padding: PaddingValues,
    hazeState: HazeState
) {
    composable<CryptoDetail> { backStackEntry ->
        val cryptoDetail: CryptoDetail = backStackEntry.toRoute()
        val symbol = cryptoDetail.symbol

        DetailRoute(
            padding = padding,
            hazeState = hazeState,
            symbol = symbol,
        )
    }
}
