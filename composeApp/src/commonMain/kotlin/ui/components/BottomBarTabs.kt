package ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ui.navigation.NavRoutes

@Composable
internal fun BottomBarTabs(
    tabs: List<BottomBarTab>,
    selectedTab: Int,
    onTabSelected: (BottomBarTab) -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        ),
        LocalContentColor provides Color.White
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            for (tab in tabs) {
                val alpha by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .35f,
                    label = "alpha"
                )
                val scale by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .98f,
                    visibilityThreshold = .000001f,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    ),
                    label = "scale"
                )
                Column(
                    modifier = Modifier
                        .scale(scale)
                        .alpha(alpha)
                        .fillMaxHeight()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                onTabSelected(tab)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    val isItemSelected = if (selectedTab == tabs.indexOf(tab)) {
                        Modifier.alpha(1f)
                    } else Modifier.alpha(.35f)
                    Icon(
                        modifier = isItemSelected,
                        imageVector = tab.icon,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

sealed class BottomBarTab(
    val route: String,
    val icon: ImageVector,
    val color: Color
) {
    data object Exchange : BottomBarTab(
        route = NavRoutes.CURRENCY_CONVERTER,
        icon = Icons.Default.CurrencyExchange,
        color = Color(0xFFFFA574) // Orange
    )

    data object Home : BottomBarTab(
        route = NavRoutes.MARKET_OVERVIEW,
        icon = Icons.Rounded.Home,
        color = Color(0xFFADFF64) // Light Green
    )

    data object Search : BottomBarTab(
        route = NavRoutes.SEARCH,
        icon = Icons.Rounded.Search,
        color = Color(0xFFFA6FFF) // Pink
    )

//    data object AiPrediction : BottomBarTab(
//        route = NavRoutes.AI_PREDICTION,
//        icon = Icons.Default.BatchPrediction,
//        color = Color(0xFF6FFFA6) // Light Green
//    ) // todo: uncomment this line

    data object Profile : BottomBarTab(
        route = NavRoutes.PROFILE,
        icon = Icons.Default.Person,
        color = Color(0xFF6FA6FF) // Light Blue
    )
}

val tabs = listOf(
    BottomBarTab.Home,
    BottomBarTab.Exchange,
    BottomBarTab.Search,
    // BottomBarTab.AiPrediction,
    BottomBarTab.Profile
)