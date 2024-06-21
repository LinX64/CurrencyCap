package ui.screens.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import di.koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.BlurColumn
import ui.screens.exchange.ExchangeViewEvent.OnAmountChange
import ui.screens.exchange.ExchangeViewEvent.OnFromChange
import ui.screens.exchange.ExchangeViewEvent.OnToChange
import ui.screens.exchange.components.AmountField
import ui.screens.exchange.components.AmountSection
import ui.screens.exchange.components.ConvertButton
import ui.screens.exchange.components.Disclaimer
import ui.screens.exchange.components.FromDropDown
import ui.screens.exchange.components.ResultText
import ui.screens.exchange.components.ToDropDown
import ui.screens.exchange.components.ToSection

@Composable
@Preview
internal fun ExchangeScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    exchangeViewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>()
) {
    val state by exchangeViewModel.viewState.collectAsState()
    val toCountryCode by exchangeViewModel.toValue.collectAsState()
    val convertedResult by exchangeViewModel.convertedResult.collectAsState()
    val amount by exchangeViewModel.amountValue.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = padding
    ) {
        item {
            ExchangeCard(
                state = state,
                exchangeViewModel = exchangeViewModel,
                toCountryCode = toCountryCode,
                convertResult = convertedResult,
                amount = amount
            )
        }
        item { Disclaimer() }
    }
}

@Composable
private fun ExchangeCard(
    modifier: Modifier = Modifier,
    state: ExchangeState,
    exchangeViewModel: ExchangeViewModel,
    toCountryCode: String,
    convertResult: String,
    amount: String
) {
    BlurColumn(modifier = modifier.padding(16.dp)) {
        Column(modifier = modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Currency Converter",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = "from",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            FromDropDown(
                exchangeState = state,
                onFromChange = { exchangeViewModel.handleEvent(OnFromChange(it)) }
            )

            ToSection()

            ToDropDown(
                exchangeState = state,
                onToChange = { exchangeViewModel.handleEvent(OnToChange(it)) }
            )

            AmountSection()

            AmountField(
                onAmountChange = { exchangeViewModel.handleEvent(OnAmountChange(it)) },
                countryCode = toCountryCode
            )

            Spacer(modifier = Modifier.height(16.dp))

            ResultText(
                result = convertResult,
                currency = toCountryCode
            )

            Spacer(modifier = Modifier.height(16.dp))

            ConvertButton(
                onConvertClicked = { exchangeViewModel.handleEvent(ExchangeViewEvent.OnConvertClick) },
                isEnabled = amount.isNotEmpty()
            )
        }
    }
}
