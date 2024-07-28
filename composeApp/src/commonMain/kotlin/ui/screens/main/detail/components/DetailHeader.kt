package ui.screens.main.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.crypto_image
import data.remote.model.main.CryptoInfo
import domain.model.ChipPeriod
import domain.model.ChipPeriod.ALL
import domain.model.ChipPeriod.DAY
import domain.model.ChipPeriod.MONTH
import domain.model.ChipPeriod.WEEK
import domain.model.ChipPeriod.YEAR
import domain.model.main.Crypto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.screens.main.overview.components.ChangeIcon
import ui.screens.main.overview.components.TopMoversChart
import ui.screens.main.overview.components.getPlaceHolder
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors

@Composable
internal fun DetailHeader(
    crypto: Crypto,
    cryptoInfo: CryptoInfo,
    isLoading: Boolean = false,
) {
    var selectedChip by remember { mutableStateOf(DAY) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlassCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACER_PADDING_16),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isLoadingModifier = if (isLoading) getPlaceHolder(
                        Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    ) else Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)

                    AsyncImage(
                        modifier = isLoadingModifier,
                        model = crypto.image,
                        contentDescription = stringResource(Res.string.crypto_image)
                    )

                    Column {
                        Text(
                            text = crypto.name + " (" + crypto.symbol.uppercase() + ")",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Market Cap: $${formatToPrice(crypto.marketCap.toDouble())}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(SPACER_PADDING_16))

                Text(
                    text = "$${formatToPrice(crypto.currentPrice)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    ChangeIcon(valueChange = crypto.priceChangePercentage24h, isLoading = isLoading)

                    Spacer(modifier = Modifier.width(SPACER_PADDING_8))

                    Text(
                        text = "${crypto.priceChangePercentage24h}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (crypto.priceChangePercentage24h > 0) CurrencyColors.Green.primary else CurrencyColors.Red.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(SPACER_PADDING_16))

                TopMoversChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    lightLineColor = CurrencyColors.Orange.primary,
                    lighterColor = CurrencyColors.Orange.primary.copy(alpha = 0.1f),
                    list = prepareChartData(cryptoInfo, selectedChip)
                )

                Spacer(modifier = Modifier.height(SPACER_PADDING_32))
            }
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        TimeRangeChips(
            selectedRange = selectedChip,
            onRangeSelected = { newRange ->
                selectedChip = newRange
            }
        )
    }
}

// todo consider moving to viewmodel

private fun prepareChartData(
    cryptoInfo: CryptoInfo,
    selectedChip: ChipPeriod
): ImmutableList<Float> {
    val percentageChange = when (selectedChip) {
        DAY -> cryptoInfo.priceChangePercentage24h
        WEEK -> cryptoInfo.priceChangePercentage7d
        MONTH -> cryptoInfo.priceChangePercentage30d
        YEAR -> cryptoInfo.priceChangePercentage1y
        ALL -> cryptoInfo.priceChangePercentage200d
    }

    val currentPrice = cryptoInfo.marketData.commonUsdPrice.usd.toFloat()
    val changeAmount = currentPrice * (percentageChange / 100f)
    val startPrice = currentPrice - changeAmount.toFloat()

    return persistentListOf(startPrice, currentPrice)
}