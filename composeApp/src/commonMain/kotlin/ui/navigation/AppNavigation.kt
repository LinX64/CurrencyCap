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
import ui.components.BottomBarTab
import ui.screens.ai_predict.navigation.aiPredictScreen
import ui.screens.auth.login.navigation.loginScreen
import ui.screens.exchange.navigation.exchangeScreen
import ui.screens.home.navigation.homeScreen
import ui.screens.profile.navigation.profileScreen
import ui.screens.search.navigation.searchScreen

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
        modifier = Modifier
            .consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        homeScreen(padding)
        searchScreen(padding)
        aiPredictScreen(padding)
        exchangeScreen(padding)
        profileScreen(padding)

        // Auth
        loginScreen(padding)
    }
}

internal fun handleNavigation(
    navController: NavHostController,
    tab: BottomBarTab
) {
    navController.navigate(tab.route) {
        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
            popUpTo(startDestinationRoute) {
                saveState = true
            }
        }
        restoreState = true
    }
}