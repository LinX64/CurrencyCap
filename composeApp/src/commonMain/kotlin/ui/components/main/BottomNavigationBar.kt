package ui.components.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_arrow_up_down
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import org.jetbrains.compose.resources.painterResource
import ui.navigation.util.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomNavigationBar(
    onTabSelected: (BottomBarTab) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    hazeState: HazeState,
    isSettingsScreen: Boolean,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val isExploreScreen = currentRoute == NavRoutes.EXPLORE
    val isAirScreen = currentRoute == NavRoutes.AI_PREDICTION
    val isDetailScreen = currentRoute?.startsWith(NavRoutes.CRYPTO_DETAIL) == true

    LaunchedEffect(currentRoute) {
        val newIndex = tabs.indexOfFirst { it.route == currentRoute }
        if (newIndex != -1) {
            selectedTabIndex = newIndex
        }
    }

    if (isSettingsScreen.not() && isExploreScreen.not() && isAirScreen.not() && isDetailScreen.not()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenteredExchangeButton(
                onButtonClicked = { onTabSelected(BottomBarTab.Exchange) }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(94.dp)
                    .hazeChild(
                        state = hazeState,
                        shape = RoundedCornerShape(35.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                        shape = RoundedCornerShape(35.dp)
                    )
            ) {
                BottomBar(
                    tabs = tabs,
                    selectedTab = selectedTabIndex,
                    onTabSelected = {
                        selectedTabIndex = tabs.indexOf(it)
                        onTabSelected(it)
                    }
                )

                UnderDashedLine(selectedTabIndex)
            }
        }
    }
}

@Composable
private fun UnderDashedLine(
    selectedTabIndex: Int
) {
    val animatedSelectedTabIndex by animateFloatAsState(
        targetValue = selectedTabIndex.toFloat(), label = "animatedSelectedTabIndex",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy,
        )
    )

    val animatedColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
        label = "animatedColor",
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val path = Path().apply {
            addRoundRect(RoundRect(size.toRect(), CornerRadius(35.dp.toPx())))
        }
        val length = PathMeasure().apply { setPath(path, false) }.length
        val tabWidth = size.width / tabs.size
        val selectedTabLeft = tabWidth * animatedSelectedTabIndex
        val selectedTabRight = selectedTabLeft + tabWidth

        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    animatedColor.copy(alpha = 0f),
                    animatedColor.copy(alpha = 1f),
                    animatedColor.copy(alpha = 1f),
                    animatedColor.copy(alpha = 0f),
                ),
                startX = selectedTabLeft,
                endX = selectedTabRight
            ),
            style = Stroke(
                width = 6f,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(length / 2, length)
                )
            )
        )
    }
}

@Composable
private fun CenteredExchangeButton(
    onButtonClicked: () -> Unit
) {
    IconButton(
        onClick = onButtonClicked,
        modifier = Modifier
            .offset(y = 40.dp)
            .size(76.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(32.dp)
            )
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(Res.drawable.ic_arrow_up_down),
            contentDescription = "Exchange",
            tint = MaterialTheme.colorScheme.surface
        )
    }
}