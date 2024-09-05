package ui.components.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import ui.components.main.BottomBarTab.BOOKMARKS
import ui.components.main.BottomBarTab.EXCHANGE
import ui.components.main.BottomBarTab.NEWS
import ui.components.main.BottomBarTab.OVERVIEW
import ui.components.main.BottomBarTab.PROFILE
import ui.screens.MainViewModel
import ui.screens.initial.landing.navigation.Landing
import ui.screens.initial.login.navigation.Login
import ui.screens.main.bookmarks.navigation.navigateToBookmarksScreen
import ui.screens.main.exchange.navigation.navigateToExchangeScreen
import ui.screens.main.news.navigation.navigateToNewsScreen
import ui.screens.main.overview.navigation.Overview
import ui.screens.main.overview.navigation.navigateToOverviewScreen
import ui.screens.main.profile.navigation.navigateToProfileScreen
import ui.screens.main.profile.navigation.Profile as ProfileScreen

@Composable
internal fun rememberAppState(
    navController: NavHostController
): AppState = remember(navController) {
    AppState(navController = navController)
}

@Stable
internal class AppState(
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
            OVERVIEW -> navController.navigateToOverviewScreen(topLevelNavOptions)
            NEWS -> navController.navigateToNewsScreen(topLevelNavOptions)
            EXCHANGE -> navController.navigateToExchangeScreen(topLevelNavOptions)
            BOOKMARKS -> navController.navigateToBookmarksScreen(topLevelNavOptions)
            PROFILE -> navController.navigateToProfileScreen(topLevelNavOptions)
        }
    }

    // TODO: Temporary solution
    private fun getTabFromRoute(route: String?): String? {
        val splitRoute = route?.split(".")
        val simpleRoute = splitRoute?.lastOrNull() ?: return null

        val tabRoute = BottomBarTab.entries.find { it.route == simpleRoute }?.route
        return tabRoute ?: simpleRoute
    }


}

internal fun navigateToOverview(
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    mainViewModel.onLoginSuccess()
    navController.navigate(Overview) {
        popUpTo(Login) { inclusive = true }
        launchSingleTop = true
    }
}

internal fun navigateToLanding(
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    mainViewModel.updateStateToNotLoggedIn()
    navController.navigate(Landing) {
        popUpTo(ProfileScreen) {
            inclusive = true
        }
    }
}
