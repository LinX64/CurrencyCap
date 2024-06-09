package ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_24
import domain.model.DataDao
import org.jetbrains.compose.resources.painterResource
import ui.common.formatToPrice
import ui.theme.colors.CurrencyColors

data class AssetInfo(
    val lastDayChange: List<Float>,
    val currentValue: Float,
)

val mockAssetInfo = AssetInfo(
    listOf(
        113.518f,
        113.799f,
        113.333f,
        113.235f,
        114.099f,
        113.506f,
        113.985f,
        114.212f,
        114.125f,
        113.531f,
        114.228f,
        113.284f,
        114.031f,
        113.493f,
        115.112f
    ),
    113.02211f
)

@Composable
internal fun RateHorizontalItem(
    modifier: Modifier = Modifier,
    icon: String,
    rate: DataDao,
    assetInfo: AssetInfo = mockAssetInfo
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .width(330.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(55.dp)),
                model = icon,
                placeholder = painterResource(Res.drawable.baseline_monetization_on_24),
                error = painterResource(Res.drawable.baseline_monetization_on_24),
                contentDescription = null
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FirstColumn(rate = rate)

                PerformanceChart(
                    modifier = Modifier.height(40.dp).width(80.dp).padding(horizontal = 10.dp),
                    list = assetInfo.lastDayChange
                )

                EndHorizontalComponents(rate = rate)
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
        modifier = modifier.padding(start = 8.dp)
    ) {
        Text(
            text = rate.symbol,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        val formattedRate = formatToPrice(rate.rateUsd.toDouble())
        Text(
            text = "$$formattedRate",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EndHorizontalComponents(
    modifier: Modifier = Modifier,
    rate: DataDao
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$898.5",
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
                    text = "%22.5",
                    color = CurrencyColors.Text_Green,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
