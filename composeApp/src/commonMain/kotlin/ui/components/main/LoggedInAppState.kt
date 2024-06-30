package ui.components.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
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
import ui.navigation.util.NavRoutes.BOOKMARKS
import ui.navigation.util.NavRoutes.EXCHANGE
import ui.navigation.util.NavRoutes.NEWS
import ui.navigation.util.NavRoutes.OVERVIEW
import ui.navigation.util.NavRoutes.PROFILE
import ui.screens.main.bookmarks.navigation.navigateToBookmarksScreen
import ui.screens.main.exchange.navigation.navigateToExchangeScreen
import ui.screens.main.news.navigation.navigateToNewsScreen
import ui.screens.main.overview.navigation.navigateToOverviewScreen
import ui.screens.main.profile.navigation.navigateToProfileScreen

@Composable
internal fun rememberLoggedInAppState(
    navController: NavHostController = rememberNavController(),
): LoggedInAppState {
    return remember(navController) {
        LoggedInAppState(navController = navController)
    }
}

@Stable
internal class LoggedInAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: BottomBarTab?
        @Composable get() = when (currentDestination?.route) {
            OVERVIEW -> Overview
            NEWS -> News
            EXCHANGE -> Exchange
            BOOKMARKS -> Bookmarks
            PROFILE -> Profile
            else -> null
        }

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

    fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BottomBarTab) =
        this?.hierarchy?.any {
            it.route?.contains(destination.route, true) ?: false
        } ?: false
}