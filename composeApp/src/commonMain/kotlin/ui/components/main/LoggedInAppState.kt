package ui.components.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ui.components.main.BottomBarTab.Bookmarks
import ui.components.main.BottomBarTab.Exchange
import ui.components.main.BottomBarTab.News
import ui.components.main.BottomBarTab.Overview
import ui.components.main.BottomBarTab.Profile
import ui.screens.main.bookmarks.navigation.navigateToBookmarksScreen
import ui.screens.main.exchange.navigation.navigateToExchangeScreen
import ui.screens.main.news.navigation.navigateToNewsScreen
import ui.screens.main.overview.navigation.navigateToOverviewScreen
import ui.screens.main.profile.navigation.navigateToProfileScreen

@Composable
internal fun rememberLoggedInAppState(
    navController: NavHostController = rememberNavController()
): LoggedInAppState = remember(navController) {
    LoggedInAppState(navController = navController)
}

@Stable
internal class LoggedInAppState(
    val navController: NavHostController
) {
    val currentDestination: String?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route.let { getTabFromRoute(it) }

    fun navigateToTopLevelDestination(topLevelDestination: BottomBarTab) {
        val topLevelNavOptions = navOptions {
            navController.graph.findStartDestination().route?.let { startRoute ->
                popUpTo(startRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            Overview -> navController.navigateToOverviewScreen(topLevelNavOptions)
            News -> navController.navigateToNewsScreen(topLevelNavOptions)
            Exchange -> navController.navigateToExchangeScreen(topLevelNavOptions)
            Bookmarks -> navController.navigateToBookmarksScreen(topLevelNavOptions)
            Profile -> navController.navigateToProfileScreen(topLevelNavOptions)
        }
    }

    // TODO: Temporary solution
    private fun getTabFromRoute(route: String?): String? {
        val simpleRoute = route?.replace("ui.navigation.util.Screen.", "")
        val key = BottomBarTab.entries.find { it.route == simpleRoute }?.route
        println("key: $key")
        return BottomBarTab.entries.find { it.route == simpleRoute }?.route
    }
}