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
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.exchange
import currencycap.composeapp.generated.resources.ic_arrow_up_down
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.navigation.util.ScreenRoutes.AI_PREDICTION
import ui.navigation.util.ScreenRoutes.CRYPTO_DETAIL
import ui.navigation.util.ScreenRoutes.EXPLORE
import ui.navigation.util.ScreenRoutes.SETTINGS
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.ICON_SIZE_48
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomNavigationBar(
    currentDestination: String?,
    scrollBehavior: TopAppBarScrollBehavior,
    hazeState: HazeState,
    isLoggedIn: Boolean,
    onTabSelected: (BottomBarTab) -> Unit
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val isExploreScreen = currentDestination == EXPLORE
    val isAIScreen = currentDestination == AI_PREDICTION
    val isDetailScreen = currentDestination?.startsWith(CRYPTO_DETAIL) == true
    val isSettingsScreen = currentDestination == SETTINGS

    LaunchedEffect(currentDestination) {
        val newIndex = tabs.indexOfFirst { it.name == currentDestination }
        if (newIndex != -1) {
            selectedTabIndex = newIndex
        }
    }

    if (isSettingsScreen.not() &&
        isExploreScreen.not() &&
        isAIScreen.not() && isDetailScreen.not() && isLoggedIn
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = SPACER_PADDING_16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenteredExchangeButton(
                onButtonClicked = { onTabSelected(BottomBarTab.Exchange) }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SPACER_PADDING_16)
                    .height(94.dp)
                    .hazeChild(
                        state = hazeState,
                        shape = RoundedCornerShape(CARD_CORNER_RADIUS)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                        shape = RoundedCornerShape(CARD_CORNER_RADIUS)
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

            Spacer(modifier = Modifier.height(SPACER_PADDING_8))
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
            addRoundRect(RoundRect(size.toRect(), CornerRadius(CARD_CORNER_RADIUS.toPx())))
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
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            CurrencyColors.Muted_Teal.primary,
            CurrencyColors.Green.primary
        )
    )

    IconButton(
        onClick = onButtonClicked,
        modifier = Modifier
            .offset(y = 40.dp)
            .size(76.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(33.dp)
            )
    ) {
        Icon(
            modifier = Modifier.size(ICON_SIZE_48),
            painter = painterResource(Res.drawable.ic_arrow_up_down),
            contentDescription = stringResource(Res.string.exchange),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}