package ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import ui.components.AppTopBar
import ui.components.BottomBarTab
import ui.components.BottomBarTab.AiPrediction
import ui.components.BottomBarTab.Exchange
import ui.components.BottomBarTab.Home
import ui.components.BottomBarTab.Search
import ui.components.BottomNavigationBar
import ui.navigation.AppNavigation
import ui.navigation.NavRoutes
import ui.screens.exchange.navigation.navigateToExchangeScreen
import ui.screens.home.navigation.navigateToHomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val currentDestination = navController.currentDestination?.route ?: ""
    val hazeState = remember { HazeState() }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                hazeState = hazeState,
                scrollBehavior = scrollBehavior,
                currentDestination = currentDestination
            )
        },
        bottomBar = {
            BottomNavigationBar(
                hazeState = hazeState,
                selectedTab = { tab -> handleTabSelection(tab, navController) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            scrollBehavior = scrollBehavior,
            padding = paddingValues
        )
    }
}

private fun handleTabSelection(
    bottomBarTab: BottomBarTab,
    navController: NavHostController
) = when (bottomBarTab) {
    Home -> navController.navigateToHomeScreen()
    Exchange -> navController.navigateToExchangeScreen()
    Search -> navController.navigate(NavRoutes.SEARCH)
    AiPrediction -> navController.navigate(NavRoutes.AI_PREDICTION)
}
