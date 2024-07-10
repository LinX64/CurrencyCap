package ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.NavRoutes
import ui.screens.main.ai_predict.navigation.aiPredictScreen
import ui.screens.main.bookmarks.navigation.bookmarksScreen
import ui.screens.main.detail.navigation.detailScreen
import ui.screens.main.exchange.navigation.exchangeScreen
import ui.screens.main.news.navigation.newsScreen
import ui.screens.main.news.news_detail.navigation.newsDetailScreen
import ui.screens.main.overview.navigation.overviewScreen
import ui.screens.main.profile.navigation.profileScreen
import ui.screens.main.search.navigation.searchScreen
import ui.screens.main.settings.navigation.settingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    hazeState: HazeState,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.OVERVIEW,
        modifier = Modifier
            .consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        searchScreen(
            padding = padding,
            navController = navController,
            hazeState = hazeState
        )
        aiPredictScreen(padding = padding, hazeState = hazeState)

        overviewScreen(
            padding = padding,
            navController = navController,
            hazeState = hazeState
        )

        bookmarksScreen(
            padding = padding,
            hazeState = hazeState,
            navController = navController
        )

        newsScreen(
            padding = padding,
            hazeState = hazeState,
            navController = navController
        )

        newsDetailScreen(
            padding = padding,
            hazeState = hazeState,
            navController = navController,
            onError = onError
        )

        detailScreen(
            padding = padding,
            hazeState = hazeState
        )

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