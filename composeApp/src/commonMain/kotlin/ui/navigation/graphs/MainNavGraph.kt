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
import dev.chrisbanes.haze.HazeState
import ui.components.main.BottomBarTab
import ui.navigation.NavRoutes
import ui.screens.ai_predict.navigation.aiPredictScreen
import ui.screens.bookmarks.navigation.bookmarksScreen
import ui.screens.exchange.navigation.exchangeScreen
import ui.screens.news.navigation.newsScreen
import ui.screens.overview.navigation.homeScreen
import ui.screens.profile.navigation.profileScreen
import ui.screens.search.navigation.searchScreen
import ui.screens.settings.navigation.settingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    hazeState: HazeState,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
        modifier = Modifier
            .consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        homeScreen(
            padding = padding,
            navController = navController,
            hazeState = hazeState,
        )
        searchScreen(padding = padding, hazeState = hazeState)
        aiPredictScreen(padding = padding, hazeState = hazeState)
        newsScreen(padding = padding, hazeState = hazeState)
        bookmarksScreen(padding = padding, hazeState = hazeState)

        exchangeScreen(
            padding = padding,
            onError = onError,
            hazeState = hazeState
        )

        profileScreen(
            padding = padding,
            onNavigateToLanding = onNavigateToLanding,
            onError = onError,
            hazeState = hazeState
        )

        settingsScreen(
            padding = padding,
            onError = onError,
            hazeState = hazeState,
            onNavigateToLanding = onNavigateToLanding
        )
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

    navController.navigate(tab.title) {
        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
            popUpTo(startDestinationRoute) {
                saveState = true
            }
        }
        restoreState = true
    }
}