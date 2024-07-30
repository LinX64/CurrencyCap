package ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import kotlinx.coroutines.launch
import ui.components.base.BaseModalBottomSheet
import ui.components.base.EdgeToEdgeScaffoldWithPullToRefresh
import ui.components.main.AppState
import ui.components.main.AppTopBar
import ui.components.main.BottomBarTab
import ui.components.main.BottomNavigationBar
import ui.components.main.navigateToLanding
import ui.components.main.navigateToOverview
import ui.components.main.rememberAppState
import ui.navigation.graphs.AppNavGraph
import ui.screens.MainState
import ui.screens.MainViewModel
import ui.screens.initial.landing.privacy_policy.PrivacyPolicySection
import ui.screens.main.news.NewsViewEvent.OnSetClick
import ui.screens.main.news.NewsViewModel
import ui.screens.main.news.components.NewsFilterSection
import ui.screens.main.overview.OverviewViewModel
import ui.screens.main.subscribers.SubscribersSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    overviewViewModel: OverviewViewModel = koinViewModel<OverviewViewModel>(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val navController = rememberNavController()
    val appState: AppState = rememberAppState(navController)
    val scope = rememberCoroutineScope()

    val mainState by mainViewModel.appState.collectAsState()
    val isRefreshing by overviewViewModel.isRefreshing.collectAsState()
    val currentDestination = appState.currentDestination
    val isLoggedIn = mainState is MainState.LoggedIn
    val hazeState = remember { HazeState() }

    EdgeToEdgeScaffoldWithPullToRefresh(
        currentDestination = currentDestination,
        isRefreshing = isRefreshing,
        onRefresh = { overviewViewModel.refresh() },
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                navController = navController,
                scrollBehavior = scrollBehavior,
                isLoggedIn = isLoggedIn,
                hazeState = hazeState,
                onFilterClick = { mainViewModel.toggleNewsFilterSheet() },
                onThemeChangeClick = mainViewModel::toggleDarkMode
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination,
                hazeState = hazeState,
                isLoggedIn = isLoggedIn,
                onTabSelected = { tab -> appState.navigateToTopLevelDestination(tab) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomSheets = {
            BaseModalBottomSheet(
                isVisible = mainViewModel.isSubscribeSheetVisible,
                onDismiss = { mainViewModel.toggleSubscribeSheet() }
            ) { SubscribersSection() }

            BaseModalBottomSheet(
                isVisible = mainViewModel.isNewsFilterSheetVisible,
                onDismiss = { mainViewModel.toggleNewsFilterSheet() }
            ) {
                NewsFilterSection(
                    sources = newsViewModel.sources.value,
                    onCloseClick = { mainViewModel.toggleNewsFilterSheet() },
                    onDoneClick = { startDate, endDate, selectedSources ->
                        mainViewModel.toggleNewsFilterSheet()
                        newsViewModel.handleEvent(OnSetClick(startDate, endDate, selectedSources))
                    }
                )
            }

            BaseModalBottomSheet(
                isVisible = mainViewModel.isPrivacyPolicySheetVisible,
                onDismiss = { mainViewModel.togglePrivacyPolicySheet() }
            ) { PrivacyPolicySection() }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            hazeState = hazeState,
            paddingValues = paddingValues,
            isLoggedIn = isLoggedIn,
            onNavigateToLanding = { navigateToLanding(mainViewModel, navController) },
            showPrivacyPolicyBottomSheet = { mainViewModel.togglePrivacyPolicySheet() },
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } },
            onLoginSuccess = { navigateToOverview(mainViewModel, navController) },
            onExploreNewsClick = { appState.navigateToTopLevelDestination(BottomBarTab.News) }
        )
    }
}