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
import ui.components.BlurBackground
import ui.screens.ai_predict.AiPredictScreen
import ui.screens.exchange.ExchangeScreen
import ui.screens.main.HomeScreen

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
            BlurBackground { HomeScreen(padding = padding) }
        }

        composable(NavRoutes.EXCHANGE) {
            BlurBackground {
                ExchangeScreen(padding = padding)
            }
        }

        composable(NavRoutes.AI_PREDICTION) {
            BlurBackground {
                AiPredictScreen()
            }
        }
    }
}