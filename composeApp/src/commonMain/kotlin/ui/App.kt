package ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import di.appModule
import di.httpClientModule
import di.repositoryModule
import di.viewModelModule
import org.koin.compose.KoinApplication
import ui.components.AppBottomBar
import ui.navigation.AppNavigation

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val currentDestination = navController.currentDestination?.route ?: ""
    val hazeState = remember { HazeState() }
    KoinApplication(application = {
        modules(appModule, httpClientModule, repositoryModule, viewModelModule)
    }) {
        Scaffold(
            bottomBar = {
                AppBottomBar(modifier = Modifier
                    .fillMaxWidth()
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
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            AppNavigation(
                navController = navController,
                padding = paddingValues,
            )
        }
    }
}
