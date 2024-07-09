package ui.components.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_arrow_left
import currencycap.composeapp.generated.resources.ic_settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import org.jetbrains.compose.resources.painterResource
import ui.navigation.util.NavRoutes
import ui.screens.main.settings.navigation.navigateToSettingsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
internal fun AppTopBar(
    currentDestination: String?,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    hazeState: HazeState
) {
    val isSettingsScreen = currentDestination == NavRoutes.SETTINGS
    val isNewsDetailScreen = currentDestination?.startsWith(NavRoutes.NEWS_DETAIL)
    val isExploreScreen = currentDestination == NavRoutes.EXPLORE
    val isAiScreen = currentDestination == NavRoutes.AI_PREDICTION

    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth()
            .hazeChild(
                state = hazeState,
                style = HazeMaterials.regular(MaterialTheme.colorScheme.surface)
            ),
        title = {
            if (isNewsDetailScreen?.not() == true) {
                Text(
                    text = currentDestination,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior,
        actions = {
//            ActionsMenu(
//                currentDestination = currentDestination,
//                navController = navController
//            )
        },
        navigationIcon = {
            if (isSettingsScreen || isNewsDetailScreen == true || isExploreScreen || isAiScreen) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

@Composable
private fun ActionsMenu(
    currentDestination: String?,
    navController: NavHostController
) {
    if (currentDestination == NavRoutes.PROFILE) {
        IconButton(
            onClick = { navController.navigateToSettingsScreen() }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = "Settings"
            )
        }
    }
}


