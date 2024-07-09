package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.main.Crypto
import ui.common.formatToPrice
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.components.ChangeIcon
import ui.screens.main.overview.components.TopMoversChart
import ui.screens.main.overview.components.getPlaceHolder
import ui.screens.main.overview.components.mockAssetInfo
import ui.theme.colors.CurrencyColors
import util.formatNumber

@Composable
internal fun CryptoContent(
    isLoading: Boolean = false,
    usd: String = "USD",
    state: OverviewState
) {
    when (state) {
        is OverviewState.Success -> {
            val cryptoRates = state.cryptoRates
            val bitcoinItem = cryptoRates.find { it.symbol == "btc" }

            CryptoSuccessBody(isLoading, bitcoinItem, usd, cryptoRates)
        }

        else -> Unit
    }
}

@Composable
private fun CryptoSuccessBody(
    isLoading: Boolean,
    bitcoinItem: Crypto?,
    usd: String,
    cryptoRates: List<Crypto>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = "Bitcoin (BTC)",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "$${formatToPrice(bitcoinItem?.currentPrice ?: 0.0)}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            InnerDropDown(isLoading, usd)
        }

        Spacer(modifier = Modifier.height(16.dp))

        InnerChartRow(isLoading = isLoading, cryptoRates = cryptoRates)
    }
}

@Composable
internal fun InnerChartRow(
    isLoading: Boolean = false,
    cryptoRates: List<Crypto>
) {
    val isPositive = cryptoRates[0].priceChangePercentage24h > 0

    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopMoversChart(
            modifier = if (isLoading) getPlaceHolder(
                Modifier
                    .width(120.dp)
                    .height(60.dp)
            ) else Modifier
                .width(120.dp)
                .height(60.dp),
            lighterColor = CurrencyColors.Green.extraLight,
            lightLineColor = CurrencyColors.Green.primary,
            list = mockAssetInfo.lastDayChange
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "Market Cap: ${formatNumber(cryptoRates[0].marketCap)}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )

            ChangeIcon(
                isPositive = isPositive,
                isLoading = isLoading,
                valueChange = 10.1,
            )
        }
    }
}

@Composable
private fun InnerDropDown(
    isLoading: Boolean,
    usd: String
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = usd,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

