package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import domain.model.main.Rate
import ui.common.formatCurrentTotal
import ui.components.GlassCard
import util.getIconBy

data class CryptoCardData(
    val value: Float,
    val valueChange: Int,
    val currentTotal: Long
)

val data = CryptoCardData(
    value = 3.689087f,
    valueChange = -18,
    currentTotal = 98160
)

@Composable
internal fun TopCryptoItem(
    modifier: Modifier = Modifier,
    cardBackground: Color = Color.Transparent,
    cardSize: Dp = 150.dp,
    dataDao: Rate,
    isLoading: Boolean = false
) {
    GlassCard {
        Card(
            modifier = modifier
                .size(cardSize)
                .clip(RoundedCornerShape(15.dp)),
            colors = CardDefaults.cardColors(containerColor = cardBackground)
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
                            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                            text = formatCurrentTotal(data.currentTotal),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelMedium
                        )

                        ChangeIcon(
                            valueChange = dataDao.rateUsd.toDouble(),
                            isPositive = data.valueChange > 0,
                            isLoading = isLoading
                        )
                    }

                    SubcomposeAsyncImage(
                        modifier = Modifier.size(24.dp).clip(CircleShape),
                        model = getIconBy(dataDao.symbol),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    ) {
                        if (isLoading) {
                            ItemPlaceHolder(modifier = Modifier.size(24.dp))
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                        text = dataDao.symbol,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                        text = "${dataDao.currencySymbol}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                        text = formatCurrentTotal(data.currentTotal),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

