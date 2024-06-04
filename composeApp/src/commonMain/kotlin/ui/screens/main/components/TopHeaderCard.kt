package ui.screens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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

data class CryptoCardData(
    val name: String,
    val value: Float,
    val valueChange: Int,
    val currentTotal: Long,
    val icon: Int
)

@Composable
fun TopHeaderCard(
    modifier: Modifier = Modifier,
    cardBackground: Color = Color.Transparent,
    cardSize: Dp = 150.dp,
) {
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
        }
    }
}

@Composable
fun CardContent(
    data: CryptoCardData = CryptoCardData(
        name = "Bitcoin",
        value = 3.689087f,
        valueChange = -18,
        currentTotal = 98160,
        icon = getBtcIcon()
    )
) {
    val textColor = Color(0xFFFFFFFF)
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

            Image(
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                painter = getIcon(data.icon),
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(textColor),
                contentDescription = null,
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