package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import ui.navigation.AppNavigation
import ui.screens.components.AppTopBar
import ui.screens.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val currentDestination = navController.currentDestination?.route ?: ""

    val hazeState = remember { HazeState() }
    val listState = rememberLazyListState()
    val showNavigationBar by remember(listState) {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                hazeState = hazeState,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = showNavigationBar,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                BottomNavigationBar(hazeState)
            }
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
