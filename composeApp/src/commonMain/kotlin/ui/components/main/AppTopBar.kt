package ui.components.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.back
import currencycap.composeapp.generated.resources.filter
import currencycap.composeapp.generated.resources.ic_arrow_left
import currencycap.composeapp.generated.resources.ic_filter_search
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.navigation.util.ScreenRoutes.AI_PREDICTION
import ui.navigation.util.ScreenRoutes.CRYPTO_DETAIL
import ui.navigation.util.ScreenRoutes.EXPLORE
import ui.navigation.util.ScreenRoutes.NEWS
import ui.navigation.util.ScreenRoutes.NEWS_DETAIL
import ui.navigation.util.ScreenRoutes.OVERVIEW
import ui.navigation.util.ScreenRoutes.PROFILE
import ui.navigation.util.ScreenRoutes.SETTINGS
import ui.screens.main.settings.navigation.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppTopBar(
    currentDestination: String?,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    hazeState: HazeState,
    isLoggedIn: Boolean,
    onFilterClick: () -> Unit,
    onThemeChangeClick: (isDark: Boolean) -> Unit
) {
    val isSettingsScreen = currentDestination == SETTINGS
    val isNewsDetailScreen = currentDestination?.startsWith(NEWS_DETAIL)
    val isDetailScreen = currentDestination?.startsWith(CRYPTO_DETAIL)
    val isExploreScreen = currentDestination == EXPLORE
    val isAiScreen = currentDestination == AI_PREDICTION
    val isNewsScreen = currentDestination == NEWS
    val isProfileScreen = currentDestination == PROFILE
    val isOverviewScreen = currentDestination == OVERVIEW

    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth()
            .hazeChild(state = hazeState),
        title = {
            if ((isNewsDetailScreen?.not() == true && isDetailScreen?.not() == true) && isLoggedIn) {
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
            scrolledContainerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            NavigationIcon(
                isSettingsScreen = isSettingsScreen,
                isNewsDetailScreen = isNewsDetailScreen,
                isExploreScreen = isExploreScreen,
                isAiScreen = isAiScreen,
                isDetailScreen = isDetailScreen,
                navController = navController
            )
        },
        actions = {
            Actions(
                isNewsScreen = isNewsScreen,
                isOverviewScreen = isOverviewScreen,
                onFilterClick = onFilterClick,
                onSettingsClick = { navController.navigate(Settings) },
                onThemeChangeClick = onThemeChangeClick
            )
        }
    )
}

@Composable
private fun NavigationIcon(
    isSettingsScreen: Boolean,
    isNewsDetailScreen: Boolean?,
    isExploreScreen: Boolean,
    isAiScreen: Boolean,
    isDetailScreen: Boolean?,
    navController: NavHostController
) {
    if (isSettingsScreen ||
        isNewsDetailScreen == true ||
        isExploreScreen ||
        isAiScreen ||
        isDetailScreen == true
    ) {
        IconButton(
            onClick = navController::navigateUp
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_left),
                contentDescription = stringResource(Res.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun Actions(
    isNewsScreen: Boolean,
    isOverviewScreen: Boolean,
    onFilterClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onThemeChangeClick: (isDark: Boolean) -> Unit
) {
    var isDarkTheme by remember { mutableStateOf(false) }

    if (isNewsScreen) {
        IconButton(
            modifier = Modifier.padding(end = 16.dp),
            onClick = onFilterClick
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_filter_search),
                contentDescription = stringResource(Res.string.filter),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    if (isOverviewScreen) {
        IconButton(
            modifier = Modifier.padding(end = 16.dp),
            onClick = {
                isDarkTheme = !isDarkTheme
                onThemeChangeClick(isDarkTheme)
            }
        ) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                contentDescription = "theme",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

//    if (isProfileScreen) {
//        IconButton(
//            modifier = Modifier.padding(end = 16.dp),
//            onClick = onSettingsClick
//        ) {
//            Icon(
//                painter = painterResource(Res.drawable.ic_settings),
//                contentDescription = "settings",
//                tint = MaterialTheme.colorScheme.onSurface
//            )
//        }
//    } // TODO: Fix this whenever there is a solution for theme on IOS
}