package ui.screens.main.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.main.CryptoInfo
import ui.common.formatToPrice
import ui.components.main.SectionRowItem
import ui.screens.main.overview.components.getPlaceHolder
import ui.screens.main.profile.components.HelpCenterBaseGlassCard
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.colors.CurrencyColors

@Composable
internal fun DetailBody(
    cryptoInfo: CryptoInfo,
    isLoading: Boolean = false
) {
    val isLoadingModifier = if (isLoading) getPlaceHolder(Modifier) else Modifier
    Column {
        SectionRowItem(title = "About ${cryptoInfo.name}")

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        HelpCenterBaseGlassCard(title = "Stats") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = isLoadingModifier,
                        text = "Market Cap",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = formatToPrice(cryptoInfo.marketData.marketCap),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = isLoadingModifier,
                        text = "Volume",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    val textColor =
                        if (cryptoInfo.marketData.priceChangePercentage24h > 0) CurrencyColors.Green.primary else CurrencyColors.Red.primary
                    Text(
                        modifier = isLoadingModifier,
                        text = "${cryptoInfo.marketData.priceChangePercentage24h}%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = isLoadingModifier,
                        text = "24h High",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = "$${formatToPrice(cryptoInfo.marketData.high24h)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = isLoadingModifier,
                        text = "24h Low",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = "$${formatToPrice(cryptoInfo.marketData.low24h)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = isLoadingModifier,
                        text = "All Time High",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = "$${formatToPrice(cryptoInfo.marketData.ath)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}