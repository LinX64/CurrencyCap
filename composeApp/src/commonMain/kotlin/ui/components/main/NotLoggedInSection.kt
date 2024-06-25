package ui.components.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.navigation.NavRoutes
import ui.navigation.graphs.AuthNavGraph
import ui.screens.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NotLoggedInSection(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    hazeState: HazeState,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: ""
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            NotLoggedInTopAppBar(
                currentDestination = currentDestination,
                navController = navController,
                isSheetOpen = isSheetOpen,
                scrollBehavior = scrollBehavior,
                hazeState = hazeState
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        AuthNavGraph(
            navController = navController,
            padding = paddingValues,
            onLoginSuccess = {
                mainViewModel.onLoginSuccess()
                navController.popBackStack()
            },
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } }
        )
    }

    if (isSheetOpen.value) ui.BottomSheet(sheetState = scaffoldState)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
private fun NotLoggedInTopAppBar(
    currentDestination: String,
    navController: NavHostController,
    isSheetOpen: MutableState<Boolean>,
    hazeState: HazeState,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth()
            .hazeChild(
                state = hazeState,
                style = HazeMaterials.regular(MaterialTheme.colorScheme.surface)
            ),
        title = { },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        navigationIcon = {
            if (isNotLoggedIn(currentDestination)) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

private fun isNotLoggedIn(currentDestination: String): Boolean = (currentDestination != NavRoutes.LANDING)
        && (currentDestination != NavRoutes.LOGIN)
        && (currentDestination != NavRoutes.REGISTER)

