package ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui.screens.components.BlurBackground
import ui.screens.exchange.ExchangeRoute
import ui.screens.main.HomeRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    padding: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
        modifier = Modifier.consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        composable(NavRoutes.HOME) {
            BlurBackground { HomeRoute(padding = padding) }
        }

        composable(NavRoutes.EXCHANGE) {
            BlurBackground {
                ExchangeRoute()
            }
        }
    }
}