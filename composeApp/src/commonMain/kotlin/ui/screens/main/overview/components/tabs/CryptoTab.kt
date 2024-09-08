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
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.bitcoin_text
import domain.model.main.Crypto
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import ui.common.formatToPrice
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.components.ChangeIcon
import ui.screens.main.overview.components.TopMoversChart
import ui.screens.main.overview.components.getPlaceHolder
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
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
            val cryptoRates = state.combinedRates.crypto
            val bitcoinItem = cryptoRates.find { it.symbol == "btc" }
            CryptoSuccessBody(isLoading, bitcoinItem, usd, cryptoRates)
        }

        else -> Unit
    }
}

@Composable
private fun CryptoSuccessBody(
    isLoading: Boolean = false,
    bitcoinItem: Crypto?,
    usd: String,
    cryptoRates: List<Crypto>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = stringResource(Res.string.bitcoin_text),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

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

            Spacer(modifier = Modifier.width(SPACER_PADDING_8))

            InnerDropDown(isLoading, usd)
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        InnerChartRow(isLoading = isLoading, cryptoRates = cryptoRates)
    }
}

@Composable
internal fun InnerChartRow(
    isLoading: Boolean = false,
    cryptoRates: List<Crypto>
) {
    val bitcoin = cryptoRates[0]
    Row(
        modifier = Modifier.padding(top = SPACER_PADDING_16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_16)
    ) {
        val priceData = persistentListOf(
            bitcoin.low24h.toFloat(),
            bitcoin.currentPrice.toFloat(),
            bitcoin.high24h.toFloat()
        )

        TopMoversChart(
            modifier = if (isLoading) getPlaceHolder(
                Modifier
                    .width(120.dp)
                    .height(60.dp)
            ) else Modifier
                .width(120.dp)
                .height(60.dp),
            shadowColor = CurrencyColors.Green.primary,
            list = priceData
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "Market Cap: ${formatNumber(bitcoin.marketCap)}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )

            ChangeIcon(
                valueChange = 10.1,
                isLoading = isLoading,
            )
        }
    }
}

@Composable
private fun InnerDropDown(
    isLoading: Boolean = false,
    usd: String
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier.padding(start = 4.dp)) else Modifier.padding(start = 4.dp),
            text = usd,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

