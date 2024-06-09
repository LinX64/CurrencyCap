package ui.screens.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import ui.screens.exchange.ExchangeViewEvent.OnConvertClick
import ui.screens.exchange.ExchangeViewEvent.OnFromChange
import ui.screens.exchange.ExchangeViewEvent.OnToChange
import ui.screens.exchange.component.AmountField
import ui.screens.exchange.component.AmountSection
import ui.screens.exchange.component.ConvertButton
import ui.screens.exchange.component.FromDropDown
import ui.screens.exchange.component.Header
import ui.screens.exchange.component.ResultText
import ui.screens.exchange.component.ToDropDown
import ui.screens.exchange.component.ToSection

@Composable
@Preview
internal fun ExchangeScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    exchangeViewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>()
) {
    val state by exchangeViewModel.viewState.collectAsState()

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

                )
        }
    }
}

@Composable
private fun ExchangeCard(
    modifier: Modifier = Modifier,
    state: ExchangeState,
    exchangeViewModel: ExchangeViewModel,
) {
    BlurColumn(
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            Header()

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
            )

            Spacer(modifier = Modifier.height(32.dp))

            ResultText(result = exchangeViewModel.convertResult.value)

            Spacer(modifier = Modifier.height(32.dp))

            ConvertButton(
                onConvertClicked = { exchangeViewModel.handleEvent(OnConvertClick) },
                isEnabled = exchangeViewModel.amountValue.value.isNotEmpty()
            )
        }
    }
}
