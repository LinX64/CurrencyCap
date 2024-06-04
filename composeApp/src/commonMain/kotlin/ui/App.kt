package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.AppBottomBar
import ui.components.AppTopBar
import ui.components.BulbBackground
import ui.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
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
            AnimatedVisibility(
                visible = showNavigationBar,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                AppBottomBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    currentDestination = currentDestination,
                    navController = navController,
                    selectedIndex = 0,
                    onItemClicked = { /*TODO*/ }
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        BulbBackground {
            AppNavigation(navController = navController, padding = paddingValues)

//            LazyVerticalGrid(
//                state = gridState,
//                columns = GridCells.Adaptive(128.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                contentPadding = paddingValues,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .testTag("lazy_grid")
//                    .nestedScroll(scrollBehavior.nestedScrollConnection)
//                    .haze(
//                        state = hazeState,
//                        style = HazeDefaults.style(backgroundColor = Color.Transparent),
//                    ),
//            ) {
//                items(10) { index ->
//                    ImageItem(
//                        text = "${index + 1}",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .aspectRatio(3 / 4f),
//                    )
//                }
//            }
        }
    }
}

