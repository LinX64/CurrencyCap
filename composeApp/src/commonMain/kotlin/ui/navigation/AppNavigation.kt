package ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ui.components.BottomBarTab
import ui.screens.ai_predict.navigation.aiPredictScreen
import ui.screens.auth.login.navigation.loginScreen
import ui.screens.auth.register.navigation.registerScreen
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
    onError: (message: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN,
        modifier = Modifier
            .consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        landingScreen(navController = navController)
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

private fun NavGraphBuilder.authGraph(
    padding: PaddingValues,
    navController: NavHostController,
    onError: (message: String) -> Unit
) {
    loginScreen(
        padding = padding,
        navController = navController,
        onError = onError
    )
    registerScreen(padding)
}

internal fun handleNavigation(
    navController: NavHostController,
    tab: BottomBarTab,
    isSheetOpen: MutableState<Boolean>
) {
    val isAiPredictionTab = tab.route == NavRoutes.AI_PREDICTION
    isSheetOpen.value = isAiPredictionTab
    if (isAiPredictionTab) {
        return
    }

    navController.navigate(tab.route) {
        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
            popUpTo(startDestinationRoute) {
                saveState = true
            }
        }
        restoreState = true
    }
}