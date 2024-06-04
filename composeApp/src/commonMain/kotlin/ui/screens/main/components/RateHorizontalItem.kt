package ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.client.currencycap.ui.common.formatToPrice
import com.client.currencycap.ui.common.getPlaceHolderDrawable
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import domain.model.DataDao
import ui.theme.colors.CurrencyColors

@Composable
fun RateHorizontalItem(
    modifier: Modifier = Modifier,
    icon: String,
    rate: DataDao
) {
    val hazeState = remember { HazeState() }

    Box(
        modifier.haze(
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
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    modifier = Modifier.size(48.dp),
                    placeholder = getPlaceHolderDrawable(),
                    model = icon,
                    error = getPlaceHolderDrawable(),
                    contentDescription = null,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    FirstColumn(rate = rate)

                    PerformanceChart(Modifier.height(40.dp).width(80.dp))

                    EndComponents()
                }
            }
        }
    }
}

@Composable
private fun FirstColumn(
    modifier: Modifier = Modifier,
    rate: DataDao
) {
    Column(
        modifier = modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = rate.symbol,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = formatToPrice(rate.rateUsd.toDouble()),
            color = Color.LightGray.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EndComponents() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "$ 1,000",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                // TODO: add upward arrow icon and downward arrow icon
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = null,
                    tint = CurrencyColors.Text_Green
                )

                Text(
                    text = "% 22.5",
                    color = CurrencyColors.Text_Green,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
