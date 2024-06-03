package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.AppBottomBar
import ui.components.BulbBackground
import ui.screens.MainState
import ui.screens.MainViewModel
import ui.screens.components.RateItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    val hazeState = remember { HazeState() }
    val currentDestination = navController.currentDestination?.route ?: ""
    val mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
    val ratesState = mainViewModel.rates.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Currency Cap")
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
                modifier = Modifier
                    .hazeChild(state = hazeState)
                    .fillMaxWidth(),
            )
        },
        bottomBar = {
            AppBottomBar(hazeState, currentDestination, navController)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        //AppNavigation(navController = navController, padding = paddingValues)

        BulbBackground {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = paddingValues
            ) {
                if (ratesState is MainState.Success) {
                    items(ratesState.rates.size) { index ->
                        RateItem(rate = ratesState.rates[index])
                    }
                }
            }
        }
    }
}

