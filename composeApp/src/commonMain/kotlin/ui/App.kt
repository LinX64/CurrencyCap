package ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import di.httpClientModule
import di.repositoryModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import ui.navigation.AppNavigation
import viewModelModule

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    KoinApplication(
        application = {
            modules(viewModelModule, repositoryModule, httpClientModule)
        }
    ) {
        AppNavigation(navController = navController)
    }
}