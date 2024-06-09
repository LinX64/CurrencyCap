package ui.screens.home.components

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_48
import domain.model.DataDao
import org.jetbrains.compose.resources.painterResource
import ui.common.formatCurrentTotal
import ui.components.BlurColumn
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
    dataDao: DataDao
) {
    BlurColumn {
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
                            text = formatCurrentTotal(data.currentTotal),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelMedium
                        )

                        ChangeIcon(data.valueChange)
                    }

                    AsyncImage(
                        modifier = Modifier.size(24.dp).clip(CircleShape),
                        model = getIconBy(dataDao.symbol),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                        error = painterResource(Res.drawable.baseline_monetization_on_48)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = dataDao.symbol,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "${dataDao.currencySymbol}",
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
    }
}

@Composable
internal fun TopCryptoItemLoading(
    modifier: Modifier = Modifier,
    cardBackground: Color = Color.Transparent,
    cardSize: Dp = 150.dp
) {
    BlurColumn {
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
                            text = formatCurrentTotal(data.currentTotal),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelMedium
                        )

                        ChangeIcon(data.valueChange)
                    }

                    Box(
                        modifier = Modifier.size(24.dp).clip(CircleShape),
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = "",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
