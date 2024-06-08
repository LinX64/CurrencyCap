package ui.screens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_btc
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import domain.model.DataDao
import org.jetbrains.compose.resources.painterResource
import ui.common.formatCurrentTotal

data class CryptoCardData(
    val name: String,
    val value: Float,
    val valueChange: Int,
    val currentTotal: Long
)

@Composable
fun TopHeaderCard(
    modifier: Modifier = Modifier,
    cardBackground: Color = Color.Transparent,
    cardSize: Dp = 150.dp,
    dataDao: DataDao
) {
    val hazeState = remember { HazeState() }

    Box(
        modifier = modifier
            .haze(
                state = hazeState,
                style = HazeDefaults.style(
                    tint = Color.White.copy(alpha = 0.1f),
                    blurRadius = 1.dp
                )
            )
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
                CardContent(dataDao = dataDao)
            }
        }
    }
}

val data = CryptoCardData(
    name = "Bitcoin",
    value = 3.689087f,
    valueChange = -18,
    currentTotal = 98160
)

@Composable
fun CardContent(
    dataDao: DataDao
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
                    text = "${dataDao.symbol}%",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelMedium
                )

                ChangeIcon(data.valueChange)
            }

            Image(
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                painter = painterResource(Res.drawable.ic_btc),
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                contentDescription = null,
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = data.name,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "${data.value}",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = formatCurrentTotal(data.currentTotal),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
