package ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.AppBottomBar
import ui.components.BulbBackground
import ui.screens.HomeRoute

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    val hazeState = remember { HazeState() }
    val currentDestination = navController.currentDestination?.route ?: ""

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(hazeState, currentDestination, navController)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        //AppNavigation(navController = navController, padding = paddingValues)

        BulbBackground {
            HomeRoute()
        }
    }
}

