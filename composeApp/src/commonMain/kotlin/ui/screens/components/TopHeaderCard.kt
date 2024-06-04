package ui.screens.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.client.currencycap.ui.common.formatCurrentTotal
import com.client.currencycap.ui.common.getArrowBottomLeftDrawable
import com.client.currencycap.ui.common.getBtcIcon
import com.client.currencycap.ui.common.getIcon
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay

data class CryptoCardData(
    val name: String,
    val value: Float,
    val valueChange: Int,
    val currentTotal: Long,
    val icon: Int
)

val CryptoLightGray = Color(0xFFf3f3f3)

@Composable
fun TopHeaderCard(
    modifier: Modifier = Modifier,
    cardBackground: Color = Color.Transparent,
    bubbleColor: Color = CryptoLightGray,
    cardSize: Dp = 150.dp,
) {
    val radius = cardSize.value / 2f
    val hazeState = remember { HazeState() }

    Box(
        modifier
            .fillMaxSize()
            .haze(
                state = hazeState,
                style = HazeDefaults.style(
                    tint = Color.White.copy(alpha = 0.1f),
                    blurRadius = 1.dp
                ),
            ),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .hazeChild(
                    state = hazeState,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Card(
                modifier = Modifier
                    .size(cardSize)
                    .clip(RoundedCornerShape(15.dp)),
                colors = CardDefaults.cardColors(containerColor = cardBackground)
            ) {
                CardContent()
            }

            var circleState by remember { mutableStateOf(CircleState.MidSize) }
            val transition = updateTransition(targetState = circleState, label = "")

            val circleRadius by transition.animateFloat(label = "",
                transitionSpec = {
                    if (targetState == CircleState.FullSize) {
                        spring(Spring.DampingRatioHighBouncy, Spring.StiffnessMedium)
                    } else {
                        spring(Spring.DampingRatioNoBouncy, Spring.StiffnessVeryLow)
                    }
                }) { state ->
                when (state) {
                    CircleState.MidSize -> radius * 0.4f
                    CircleState.FullSize -> radius * 0.8f
                }
            }

            LaunchedEffect(Unit) {
                delay(500)
                circleState = CircleState.FullSize
            }

            Canvas(modifier = Modifier.size(cardSize), onDraw = {
                drawCircle(
                    color = bubbleColor,
                    radius = circleRadius,
                    center = Offset(
                        x = size.width - radius + (radius * 0.2f),
                        y = radius - (radius * 0.2f)
                    )
                )
            })
        }
    }
}

@Composable
fun CardContent(
    data: CryptoCardData = CryptoCardData(
        name = "Bitcoin",
        icon = getBtcIcon(),
        value = 3.689087f,
        valueChange = -18,
        currentTotal = 98160
    )
) {
    CryptoCardContent(data, Color(0xFFFFFFFF))
}

@Composable
private fun CryptoCardContent(
    data: CryptoCardData,
    textColor: Color
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.size(150.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "${data.valueChange}%",
                    color = textColor,
                    style = MaterialTheme.typography.labelMedium
                )

                ChangeIcon(data.valueChange)
            }

            Icon(
                painter = getIcon(data.icon),
                contentDescription = "Card Icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }


        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = data.name,
                color = textColor,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "${data.value}",
                color = textColor,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = formatCurrentTotal(data.currentTotal),
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun ChangeIcon(valueChange: Int = -18) {
    var iconModifier: Modifier = Modifier
    val tint: Color
    val contentDescription: String

    if (valueChange > 0) {
        tint = Color(0xFFFFFFFF)
        iconModifier = Modifier.rotate(180f)
        contentDescription = "Arrow Up"
    } else {
        tint = Color(0xFFa97d72)
        contentDescription = "Arrow Down"
    }

    Icon(
        modifier = iconModifier.size(17.dp),
        painter = getArrowBottomLeftDrawable(),
        contentDescription = contentDescription,
        tint = tint
    )
}

private enum class CircleState {
    MidSize,
    FullSize
}