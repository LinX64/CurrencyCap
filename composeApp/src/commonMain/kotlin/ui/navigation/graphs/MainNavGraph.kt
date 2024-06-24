package ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ui.components.BottomBarTab
import ui.navigation.NavRoutes
import ui.screens.ai_predict.navigation.aiPredictScreen
import ui.screens.exchange.navigation.exchangeScreen
import ui.screens.overview.navigation.overviewScreen
import ui.screens.profile.navigation.profileScreen
import ui.screens.search.navigation.searchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MARKET_OVERVIEW,
        modifier = Modifier
            .consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        overviewScreen(padding)
        searchScreen(padding)
        aiPredictScreen(padding)
        exchangeScreen(padding)
        profileScreen(padding)
    }
}

internal fun handleNavigation(
    navController: NavHostController,
    tab: BottomBarTab,
    isSheetOpen: MutableState<Boolean>
) {
//    val isAiPredictionTab = tab.route == NavRoutes.AI_PREDICTION
//    isSheetOpen.value = isAiPredictionTab
//    if (isAiPredictionTab) return

    navController.navigate(tab.route) {
        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
            popUpTo(startDestinationRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}