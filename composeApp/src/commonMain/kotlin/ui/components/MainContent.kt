package ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.article_added_to_bookmarks
import currencycap.composeapp.generated.resources.article_removed_from_bookmarks
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import ui.components.main.AppState
import ui.components.main.BottomBarTab
import ui.components.main.navigateToLanding
import ui.components.main.navigateToOverview
import ui.navigation.graphs.AppNavGraph
import ui.navigation.util.ScreenRoutes.LIVE_PRICES
import ui.navigation.util.ScreenRoutes.OVERVIEW
import ui.screens.MainViewModel
import ui.screens.SheetType.ABOUT_US
import ui.screens.SheetType.PRIVACY_POLICY
import ui.screens.main.assets_live_price.AssetsLivePriceViewModel
import ui.screens.main.overview.OverviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(
    appState: AppState,
    paddingValues: PaddingValues,
    hazeState: HazeState,
    isLoggedIn: Boolean,
    mainViewModel: MainViewModel,
    assetsLivePriceViewModel: AssetsLivePriceViewModel = koinViewModel(),
    overviewViewModel: OverviewViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navController: NavHostController
) {
    val isOverviewScreen = appState.currentDestination == OVERVIEW
    val isLivePricesScreen = appState.currentDestination == LIVE_PRICES
    val isRefreshing by overviewViewModel.isRefreshing.collectAsStateWithLifecycle()
    val isLivePricesRefreshing by assetsLivePriceViewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .pullToRefresh(
                isOverviewScreen = isOverviewScreen,
                isLivePricesScreen = isLivePricesScreen,
                pullToRefreshState = pullToRefreshState,
                isRefreshing = if (isOverviewScreen) isRefreshing else isLivePricesRefreshing,
                onRefresh = { overviewViewModel.refresh() },
                onLivePricesRefresh = { assetsLivePriceViewModel.refresh() }
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        AppNavGraph(
            navController = navController,
            hazeState = hazeState,
            paddingValues = paddingValues,
            isLoggedIn = isLoggedIn,
            onNavigateToLanding = { navigateToLanding(mainViewModel, navController) },
            showPrivacyPolicyBottomSheet = { mainViewModel.toggleSheet(PRIVACY_POLICY) },
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } },
            onLoginSuccess = { navigateToOverview(mainViewModel, navController) },
            onExploreNewsClick = { appState.navigateToTopLevelDestination(BottomBarTab.NEWS) },
            onShowAboutUsBottomSheet = { mainViewModel.toggleSheet(ABOUT_US) },
            showBookmarkConfirmationSnakeBar = { isBookmarked ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (isBookmarked) getString(Res.string.article_added_to_bookmarks)
                        else getString(Res.string.article_removed_from_bookmarks),
                        duration = SnackbarDuration.Short
                    )
                }
            })

        if (isOverviewScreen || isLivePricesScreen) {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun Modifier.pullToRefresh(
    isOverviewScreen: Boolean,
    isLivePricesScreen: Boolean,
    pullToRefreshState: PullToRefreshState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onLivePricesRefresh: () -> Unit
): Modifier {
    return when {
        isOverviewScreen -> {
            this.pullToRefresh(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
            )
        }

        isLivePricesScreen -> {
            this.pullToRefresh(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = onLivePricesRefresh
            )
        }

        else -> {
            this
        }
    }
}
