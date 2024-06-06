package ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import ui.components.AppBottomBar
import ui.components.AppTopBar
import ui.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val currentDestination = navController.currentDestination?.route ?: ""
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val hazeState = remember { HazeState() }

    Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                currentDestination = navController.currentDestination,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            AppBottomBar(modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .haze(
                    state = hazeState,
                    style = HazeDefaults.style(
                        tint = Color.White.copy(alpha = 0.1f),
                        blurRadius = 1.dp
                    )
                ),
                currentDestination = currentDestination,
                selectedIndex = 0,
                onItemClicked = { /*TODO*/ })
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        AppNavigation(navController = navController, padding = paddingValues)
    }
}

