package ui.navigation

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
import ui.screens.ai_predict.navigation.aiPredictScreen
import ui.screens.exchange.navigation.exchangeScreen
import ui.screens.landing.navigation.landingScreen
import ui.screens.overview.navigation.overviewScreen
import ui.screens.profile.navigation.profileScreen
import ui.screens.search.navigation.searchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    padding: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior,
    onError: (message: String) -> Unit,
    isUserLoggedIn: Boolean = false,
    uid: String = ""
) {
    val initialRoute = if (isUserLoggedIn) {
        NavRoutes.MARKET_OVERVIEW + "/${uid}"
    } else NavRoutes.LANDING

    NavHost(
        navController = navController,
        startDestination = initialRoute,
        modifier = Modifier
            .consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        landingScreen(navController)

        authGraph(
            padding = padding,
            navController = navController,
            onError = onError
        )

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
    val isAiPredictionTab = tab.route == NavRoutes.AI_PREDICTION
    isSheetOpen.value = isAiPredictionTab
    if (isAiPredictionTab) return

    navController.navigate(tab.route) {
        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
            popUpTo(startDestinationRoute) {
                saveState = true
            }
        }
        restoreState = true
    }
}