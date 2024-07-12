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
import ui.navigation.util.ScreenRoutes.BOOKMARKS
import ui.navigation.util.ScreenRoutes.EXCHANGE
import ui.navigation.util.ScreenRoutes.NEWS
import ui.navigation.util.ScreenRoutes.OVERVIEW
import ui.navigation.util.ScreenRoutes.PROFILE
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
            .currentBackStackEntryAsState().value?.destination?.route

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

    private fun getTabFromRoute(route: String?): String? {
        println("route: $route") // ui.navigation.util.Screen.Overview
        return when (route) {
            OVERVIEW -> Overview.screen
            NEWS -> News.screen
            EXCHANGE -> Exchange.screen
            BOOKMARKS -> Bookmarks.screen
            PROFILE -> Profile.screen
            else -> null
        }
    }
}