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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_48
import currencycap.composeapp.generated.resources.crypto_image
import domain.model.main.Crypto
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.theme.AppDimensions.ICON_SIZE_48
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors

@Composable
internal fun CryptoHorizontalItem(
    modifier: Modifier = Modifier,
    crypto: Crypto,
    isLoading: Boolean = false,
    onClick: (id: String, symbol: String) -> Unit,
) {
    GlassCard(
        isClickable = true,
        onCardClick = { onClick(crypto.id, crypto.symbol) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(SPACER_PADDING_16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLoading) {
                ItemPlaceHolder(modifier = Modifier.size(ICON_SIZE_48))
            } else {
                AsyncImage(
                    modifier = Modifier.size(ICON_SIZE_48).clip(RoundedCornerShape(55.dp)),
                    model = crypto.image,
                    placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                    error = painterResource(Res.drawable.baseline_monetization_on_48),
                    contentDescription = stringResource(Res.string.crypto_image)
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
        modifier = modifier.padding(start = SPACER_PADDING_8)
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = crypto.symbol.uppercase(),
            color = MaterialTheme.colorScheme.onSurface,
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
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                ChangeIcon(
                    valueChange = crypto.priceChangePercentage24h,
                    isLoading = isLoading
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
