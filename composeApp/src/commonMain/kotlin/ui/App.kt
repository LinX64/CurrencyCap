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
import ui.navigation.AppNavigation
import ui.screens.components.BottomNavigationBar

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val hazeState = remember { HazeState() }
    val currentDestination = navController.currentDestination?.route ?: ""
    Scaffold(
        bottomBar = { BottomNavigationBar(hazeState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            padding = paddingValues
        )
    }
}
