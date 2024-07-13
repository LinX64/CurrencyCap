package ui.components.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze

@Composable
internal fun VerticalBarCard(
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(1) }
    val hazeState = remember { HazeState() }
    Box(
        modifier = Modifier.then(modifier)
            .width(64.dp)
            .height(160.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                shape = RoundedCornerShape(35.dp)
            )
            .clip(RoundedCornerShape(35.dp))
            .haze(
                state = hazeState,
                style = HazeStyle(
                    tint = MaterialTheme.colorScheme.primary,
                    blurRadius = 35.dp,
                    noiseFactor = 20f
                )
            )
    ) {
        VerticalBar(
            tabs = myTabs,
            selectedTab = selectedTabIndex,
            onTabSelected = {
                selectedTabIndex = myTabs.indexOf(it)
                onTabSelected(selectedTabIndex)
            }
        )

        SidesDash(selectedTabIndex)
    }
}

@Composable
private fun SidesDash(
    selectedTabIndex: Int,
    tabsCount: Int = 3,
    horizontalPaddingBetweenDashes: Dp = 1.dp
) {
    val animatedSelectedTabIndex by animateFloatAsState(
        targetValue = selectedTabIndex.toFloat(), label = "animatedSelectedTabIndex",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy,
        )
    )

    val yellowColor = Color.Yellow

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val tabHeight = size.height / tabsCount
        val selectedTabTop = tabHeight * animatedSelectedTabIndex
        val dashWidth = 6.dp.toPx()
        val dashHeight = 20.dp.toPx()
        val horizontalPadding = horizontalPaddingBetweenDashes.toPx()

        // Calculate the horizontal padding correctly
        val dashLeftX = horizontalPadding
        val dashRightX = size.width - horizontalPadding - dashWidth

        // Draw left vertical dash
        drawRoundRect(
            color = yellowColor,
            topLeft = Offset(dashLeftX, selectedTabTop - dashHeight / 2 + tabHeight / 2),
            size = Size(dashWidth, dashHeight),
            cornerRadius = CornerRadius(dashWidth / 2, dashWidth / 2)
        )

        // Draw right vertical dash
        drawRoundRect(
            color = yellowColor,
            topLeft = Offset(dashRightX, selectedTabTop - dashHeight / 2 + tabHeight / 2),
            size = Size(dashWidth, dashHeight),
            cornerRadius = CornerRadius(dashWidth / 2, dashWidth / 2)
        )
    }
}
