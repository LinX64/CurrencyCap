package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import currencycap.composeapp.generated.resources.baseline_monetization_on_48
import domain.model.main.Crypto
import org.jetbrains.compose.resources.painterResource
import ui.common.formatToPrice
import ui.components.GlassCard
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
internal fun CryptoHorizontalItem(
    modifier: Modifier = Modifier,
    crypto: Crypto,
    isLoading: Boolean = false,
    assetInfo: AssetInfo = mockAssetInfo,
) {
    GlassCard {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLoading) {
                ItemPlaceHolder(modifier = Modifier.size(48.dp))
            } else {
                AsyncImage(
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(55.dp)),
                    model = crypto.image,
                    placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                    error = painterResource(Res.drawable.baseline_monetization_on_48),
                    contentDescription = null
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FirstHorizontalColumn(crypto = crypto, isLoading = isLoading)

//                PerformanceChart(
//                    modifier = Modifier.height(40.dp).width(80.dp),
//                    list = assetInfo.lastDayChange
//                )

                // TODO: add PerformanceChart

                EndHorizontalComponents(crypto = crypto, isLoading = isLoading)
            }
        }
    }
}

@Composable
private fun FirstHorizontalColumn(
    modifier: Modifier = Modifier,
    crypto: Crypto,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier.padding(start = 8.dp)
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = crypto.symbol,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        val formattedRate = formatToPrice(crypto.currentPrice)
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
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
    isLoading: Boolean,
    crypto: Crypto
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            val formattedRate = "$${crypto.priceChangePercentage24h}"
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = formattedRate,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                ChangeIcon(
                    isLoading = isLoading,
                    valueChange = crypto.priceChangePercentage24h,
                    isPositive = crypto.priceChangePercentage24h > 0
                )

                val formattedPriceChange = formatToPrice(crypto.priceChangePercentage24h)
                val priceChange24Hour = "$formattedPriceChange%"
                Text(
                    modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                    text = priceChange24Hour,
                    color = if (crypto.priceChangePercentage24h > 0) CurrencyColors.Green.primary else CurrencyColors.Red.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
