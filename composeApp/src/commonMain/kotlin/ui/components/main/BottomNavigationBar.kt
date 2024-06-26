package ui.components.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@Composable
internal fun BottomNavigationBar(
    hazeState: HazeState,
    onTabSelected: (BottomBarTab) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenteredExchangeButton()

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
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .3f),
                    shape = RoundedCornerShape(35.dp)
                ),
        ) {
            BottomBar(
                tabs,
                selectedTab = selectedTabIndex,
                onTabSelected = {
                    selectedTabIndex = tabs.indexOf(it)
                    onTabSelected(it)
                }
            )

//            Canvas(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(RoundedCornerShape(35.dp))
//                    .blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
//            ) {
//                val tabWidth = size.width / tabs.size
//                drawCircle(
//                    color = animatedColor.copy(alpha = .6f),
//                    radius = size.height / 2,
//                    center = Offset(
//                        (tabWidth * animatedSelectedTabIndex) + tabWidth / 2,
//                        size.height / 2
//                    )
//                )
//            }


            /**
             * This adds a dashed line to the bottom bar
             */
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(35.dp))
            ) {
                val path = Path().apply {
                    addRoundRect(RoundRect(size.toRect(), CornerRadius(size.height)))
                }
                val length = PathMeasure().apply { setPath(path, false) }.length
                val tabWidth = size.width / tabs.size

                drawPath(
                    path,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            animatedColor.copy(alpha = 0f),
                            animatedColor.copy(alpha = 1f),
                            animatedColor.copy(alpha = 1f),
                            animatedColor.copy(alpha = 0f),
                        ),
                        startX = tabWidth * animatedSelectedTabIndex,
                        endX = tabWidth * (animatedSelectedTabIndex + 1),
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
    }
}

@Composable
private fun CenteredExchangeButton() {
    IconButton(
        onClick = { },
        modifier = Modifier
            .offset(y = 40.dp)
            .size(76.dp)
            .background(
                MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(32.dp)
            )
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.SwapVert,
            contentDescription = "Swap currencies",
            tint = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun BottomBar(
    tabs: List<BottomBarTab>,
    selectedTab: Int,
    onTabSelected: (BottomBarTab) -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(fontSize = 12.sp),
        LocalContentColor provides Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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

                    if (tab.icon != null) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = tab.title,
                        modifier = isItemSelected,
                        fontWeight = if (selectedTab == tabs.indexOf(tab)) FontWeight.Bold else FontWeight.Normal,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

//@Composable
//internal fun BottomNavigationBar(
//    hazeState: HazeState,
//    onTabSelected: (BottomBarTab) -> Unit
//) {
//    var selectedTabIndex by remember { mutableIntStateOf(0) }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 32.dp, vertical = 16.dp)
//            .height(64.dp)
//            .hazeChild(state = hazeState, shape = CircleShape)
//            .border(
//                width = 2.dp,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
//                shape = CircleShape
//            )
//    ) {
//        BottomBarTabs(
//            tabs,
//            selectedTab = selectedTabIndex,
//            onTabSelected = {
//                selectedTabIndex = tabs.indexOf(it)
//                onTabSelected(it)
//            }
//        )
//
//        val animatedSelectedTabIndex by animateFloatAsState(
//            targetValue = selectedTabIndex.toFloat(), label = "animatedSelectedTabIndex",
//            animationSpec = spring(
//                stiffness = Spring.StiffnessLow,
//                dampingRatio = Spring.DampingRatioLowBouncy,
//            )
//        )
//
//        val animatedColor by animateColorAsState(
//            targetValue = tabs[selectedTabIndex].color,
//            label = "animatedColor",
//            animationSpec = spring(
//                stiffness = Spring.StiffnessLow,
//            )
//        )
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//                .blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
//        ) {
//            val tabWidth = size.width / tabs.size
//            drawCircle(
//                color = animatedColor.copy(alpha = .6f),
//                radius = size.height / 2,
//                center = Offset(
//                    (tabWidth * animatedSelectedTabIndex) + tabWidth / 2,
//                    size.height / 2
//                )
//            )
//        }
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//        ) {
//            val path = Path().apply {
//                addRoundRect(RoundRect(size.toRect(), CornerRadius(size.height)))
//            }
//            val length = PathMeasure().apply { setPath(path, false) }.length
//            val tabWidth = size.width / tabs.size
//
//            drawPath(
//                path,
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        animatedColor.copy(alpha = 0f),
//                        animatedColor.copy(alpha = 1f),
//                        animatedColor.copy(alpha = 1f),
//                        animatedColor.copy(alpha = 0f),
//                    ),
//                    startX = tabWidth * animatedSelectedTabIndex,
//                    endX = tabWidth * (animatedSelectedTabIndex + 1),
//                ),
//                style = Stroke(
//                    width = 6f,
//                    pathEffect = PathEffect.dashPathEffect(
//                        intervals = floatArrayOf(length / 2, length)
//                    )
//                )
//            )
//        }
//    }
//}
