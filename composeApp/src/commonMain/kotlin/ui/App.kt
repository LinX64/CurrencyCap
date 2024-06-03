package ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import appModule
import di.httpClientModule
import di.repositoryModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.currentKoinScope
import ui.navigation.AppNavigation
import viewModelModule

@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    MaterialTheme {
        KoinApplication(
            application = {
                modules(appModule, viewModelModule, repositoryModule, httpClientModule)
            }) {
            AppNavigation(navController = navController)
        }
    }
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}