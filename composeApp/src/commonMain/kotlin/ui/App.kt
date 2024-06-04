package ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import dev.chrisbanes.haze.hazeChild
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.AppBottomBar
import ui.components.AppTopBar
import ui.components.BulbBackground
import ui.navigation.AppNavigation

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    val currentDestination = navController.currentDestination?.route ?: ""
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val hazeState = remember { HazeState() }
    val gridState = rememberLazyGridState()
    val showNavigationBar by remember(gridState) {
        derivedStateOf { gridState.firstVisibleItemIndex == 0 }
    }
    Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                currentDestination = navController.currentDestination,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            AppBottomBar(
                modifier = Modifier
                    .hazeChild(hazeState)
                    .fillMaxWidth(),
                showNavigationBar = showNavigationBar,
                currentDestination = currentDestination,
                selectedIndex = 0,
                onItemClicked = { /*TODO*/ }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        BulbBackground {
            AppNavigation(navController = navController, padding = paddingValues)
        }
    }
}

