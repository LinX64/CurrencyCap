package ui.components.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun VerticalBar(
    tabs: List<VerticalBarTab>,
    selectedTab: Int,
    onTabSelected: (VerticalBarTab) -> Unit,
) {
    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            for (tab in tabs) {
                val isSelected = selectedTab == tabs.indexOf(tab)

                val alpha by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .35f,
                    label = "alpha"
                )

                val scale by animateFloatAsState(
                    targetValue = if (selectedTab == tabs.indexOf(tab)) 1f else .98f,
                    visibilityThreshold = .000001f,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
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
                    VerticalBarIcon(isSelected, tab)
                }
            }
        }
    }
}

@Composable
private fun VerticalBarIcon(
    isSelected: Boolean,
    tab: VerticalBarTab
) {
    Box(
        modifier = if (isSelected) {
            Modifier
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                        )
                    ),
                    shape = CircleShape
                )
        } else Modifier
    ) {
        Icon(
            modifier = Modifier.size(24.dp).align(Alignment.Center),
            painter = painterResource(tab.icon),
            contentDescription = tab.title
        )
    }
}
