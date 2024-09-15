package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.article_added_to_bookmarks
import currencycap.composeapp.generated.resources.article_removed_from_bookmarks
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import di.koinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.base.BaseModalBottomSheet
import ui.components.main.AppState
import ui.components.main.AppTopBar
import ui.components.main.BottomBarTab
import ui.components.main.BottomNavigationBar
import ui.components.main.navigateToLanding
import ui.components.main.navigateToOverview
import ui.components.main.rememberAppState
import ui.navigation.graphs.AppNavGraph
import ui.navigation.util.ScreenRoutes.OVERVIEW
import ui.screens.MainViewModel
import ui.screens.SheetType.ABOUT_US
import ui.screens.SheetType.NEWS_FILTER
import ui.screens.SheetType.PRIVACY_POLICY
import ui.screens.SheetType.SUBSCRIBE
import ui.screens.initial.landing.privacy_policy.PrivacyPolicySection
import ui.screens.main.MainState.LoggedIn
import ui.screens.main.news.NewsFilterSection
import ui.screens.main.overview.OverviewViewModel
import ui.screens.main.settings.AboutUsSection
import ui.screens.main.subscribers.SubscribersSection
import ui.theme.AppM3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
internal fun App(
    mainViewModel: MainViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
) {
    val navController = rememberNavController()
    val appState: AppState = rememberAppState(navController)
    val scope = rememberCoroutineScope()
    val overviewViewModel = koinViewModel<OverviewViewModel>()
    val isRefreshing by overviewViewModel.isRefreshing.collectAsStateWithLifecycle()
    val mainState by mainViewModel.viewState.collectAsStateWithLifecycle()
    val isDarkMode by mainViewModel.isDark.collectAsStateWithLifecycle()
    val isLoggedIn = mainState is LoggedIn
    val currentDestination = appState.currentDestination
    val hazeState = remember { HazeState() }
    val pullToRefreshState = rememberPullToRefreshState()
    val isOverviewScreen = currentDestination == OVERVIEW

    AppM3Theme(isDarkMode = isDarkMode) {
        Scaffold(
            topBar = {
                AppTopBar(
                    currentDestination = currentDestination,
                    navController = navController,
                    scrollBehavior = scrollBehavior,
                    hazeState = hazeState,
                    isLoggedIn = isLoggedIn,
                    onFilterClick = { mainViewModel.toggleSheet(NEWS_FILTER) }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    hazeState = hazeState,
                    onTabSelected = { tab -> appState.navigateToTopLevelDestination(tab) }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .then(
                        if (isOverviewScreen) {
                            Modifier.pullToRefresh(
                                state = pullToRefreshState,
                                isRefreshing = isRefreshing,
                                onRefresh = { overviewViewModel.refresh() }
                            )
                        } else Modifier
                    )
                    .hazeChild(hazeState),
                contentAlignment = Alignment.TopCenter
            ) {
                NavGraph(
                    navController = navController,
                    paddingValues = paddingValues,
                    hazeState = hazeState,
                    isLoggedIn = isLoggedIn,
                    mainViewModel = mainViewModel,
                    appState = appState,
                    snackbarHostState = snackbarHostState,
                    scope = scope
                )

                if (isOverviewScreen) {
                    PullToRefreshDefaults.Indicator(
                        state = pullToRefreshState,
                        isRefreshing = isRefreshing,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            BottomSheets(mainViewModel = mainViewModel)
        }
    }
}

@Composable
private fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    hazeState: HazeState,
    isLoggedIn: Boolean,
    mainViewModel: MainViewModel,
    appState: AppState,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
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
}

@Composable
private fun BottomSheets(
    mainViewModel: MainViewModel
) {
    BaseModalBottomSheet(
        isVisible = mainViewModel.isSubscribeSheetVisible,
        onDismiss = { mainViewModel.toggleSheet(SUBSCRIBE) }) { SubscribersSection() }
    BaseModalBottomSheet(
        isVisible = mainViewModel.isPrivacyPolicySheetVisible,
        onDismiss = { mainViewModel.toggleSheet(PRIVACY_POLICY) }) { PrivacyPolicySection() }
    BaseModalBottomSheet(
        isVisible = mainViewModel.isAboutUsSheetVisible,
        onDismiss = { mainViewModel.toggleSheet(ABOUT_US) }) { AboutUsSection() }
    BaseModalBottomSheet(
        isVisible = mainViewModel.isNewsFilterSheetVisible,
        onDismiss = { mainViewModel.toggleSheet(NEWS_FILTER) }) {
        NewsFilterSection(
            onCloseClick = { mainViewModel.toggleSheet(NEWS_FILTER) }
        )
    }
}
