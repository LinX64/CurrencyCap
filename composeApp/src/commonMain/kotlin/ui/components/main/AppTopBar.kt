package ui.components.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.back
import currencycap.composeapp.generated.resources.filter
import currencycap.composeapp.generated.resources.ic_arrow_left
import currencycap.composeapp.generated.resources.ic_filter_search
import currencycap.composeapp.generated.resources.ic_presention_chart
import currencycap.composeapp.generated.resources.ic_settings
import currencycap.composeapp.generated.resources.live_prices
import currencycap.composeapp.generated.resources.settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.navigation.util.ScreenRoutes
import ui.screens.main.assets_live_price.navigation.LivePrices
import ui.screens.main.settings.navigation.navigateToSettingsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
internal fun AppTopBar(
    currentDestination: String?,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    hazeState: HazeState,
    isLoggedIn: Boolean,
    onFilterClick: () -> Unit,
) {
    val destination = currentDestination?.substringBefore("/")
    val screenConfig =
        remember(currentDestination) { ScreenConfig.fromDestination(destination) }

    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
//            .hazeChild(
//                state = hazeState,
//                style = HazeMaterials.regular(MaterialTheme.colorScheme.surface),
//                mask = remember { Brush.easedVerticalGradient(easing = EaseInOut) },
//            ),
        , title = {
            if (screenConfig.showTitle && isLoggedIn) {
                Text(
                    text = destination.orEmpty(),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = { if (screenConfig.showBackButton) BackButton(navController) },
        actions = {
            TopBarActions(
                screenConfig = screenConfig,
                onFilterClick = onFilterClick,
                onSettingsClick = { navController.navigateToSettingsScreen() },
                onLivePricesClick = { navController.navigate(LivePrices) }
            )
        }
    )
}

@Composable
private fun BackButton(navController: NavHostController) {
    IconButton(onClick = navController::navigateUp) {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_left),
            contentDescription = stringResource(Res.string.back),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun TopBarActions(
    screenConfig: ScreenConfig,
    onFilterClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLivePricesClick: () -> Unit
) {
    when (screenConfig) {
        ScreenConfig.NEWS -> FilterButton(onFilterClick)
        ScreenConfig.OVERVIEW -> LivePricesButton(onLivePricesClick)
        ScreenConfig.PROFILE -> SettingsButton(onSettingsClick)
        else -> {}
    }
}

@Composable
private fun FilterButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.padding(end = 16.dp),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_filter_search),
            contentDescription = stringResource(Res.string.filter),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun LivePricesButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(Res.drawable.ic_presention_chart),
            contentDescription = stringResource(Res.string.live_prices),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SettingsButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.padding(end = 16.dp),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_settings),
            contentDescription = stringResource(Res.string.settings),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

enum class ScreenConfig(
    val route: String,
    val showBackButton: Boolean,
    val showTitle: Boolean
) {
    SETTINGS(ScreenRoutes.SETTINGS, true, true),
    NEWS_DETAIL(ScreenRoutes.NEWS_DETAIL, true, true),
    CRYPTO_DETAIL(ScreenRoutes.CRYPTO_DETAIL, true, true),
    EXPLORE(ScreenRoutes.EXPLORE, true, false),
    AI_PREDICTION(ScreenRoutes.AI_PREDICTION, true, false),
    NEWS(ScreenRoutes.NEWS, false, true),
    OVERVIEW(ScreenRoutes.OVERVIEW, false, true),
    PROFILE(ScreenRoutes.PROFILE, false, true),
    CRYPTO_LIST(ScreenRoutes.CRYPTO_LIST, true, true),
    TOP_RATES(ScreenRoutes.TOP_RATES, true, true),
    LIVE_PRICES(ScreenRoutes.LIVE_PRICES, true, true),
    OTHER("", false, true);

    companion object {
        fun fromDestination(destination: String?): ScreenConfig {
            return entries.find { it.route == destination } ?: OTHER
        }
    }
}