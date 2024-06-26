package ui.screens.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.BlurColumn
import ui.components.HandleNavigationEffect
import ui.screens.exchange.ExchangeViewEvent.OnAmountChange
import ui.screens.exchange.ExchangeViewEvent.OnConvertClick
import ui.screens.exchange.ExchangeViewEvent.OnFromChange
import ui.screens.exchange.ExchangeViewEvent.OnToChange
import ui.screens.exchange.components.AmountField
import ui.screens.exchange.components.AmountSection
import ui.screens.exchange.components.ConvertButton
import ui.screens.exchange.components.Disclaimer
import ui.screens.exchange.components.FromDropDown
import ui.screens.exchange.components.FromSection
import ui.screens.exchange.components.ResultText
import ui.screens.exchange.components.SwapButton
import ui.screens.exchange.components.ToDropDown
import ui.screens.exchange.components.ToSection

@Composable
@Preview
internal fun ExchangeScreen(
    exchangeViewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>(),
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    onError: (String) -> Unit
) {
    val state by exchangeViewModel.viewState.collectAsStateWithLifecycle()
    val fromCountryCode by exchangeViewModel.fromValue.collectAsState()
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
                fromCountryCode = fromCountryCode,
                toCountryCode = toCountryCode,
                convertResult = convertedResult,
                amount = amount
            )
        }
        item { Disclaimer() }
    }

    HandleNavigationEffect(exchangeViewModel) { effect ->
        when (effect) {
            is ExchangeNavigationEffect.ShowSnakeBar -> onError(effect.message)
        }
    }
}

@Composable
private fun ExchangeCard(
    modifier: Modifier = Modifier,
    exchangeViewModel: ExchangeViewModel,
    state: ExchangeState,
    fromCountryCode: String,
    toCountryCode: String,
    convertResult: String,
    amount: String
) {
    val selectedToValue = remember { mutableStateOf(toCountryCode) }
    val selectedFromValue = remember { mutableStateOf(fromCountryCode) }
    val shouldShowConvert = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    BlurColumn(
        modifier = modifier.padding(24.dp)
    ) {
        FromSection()

        FromDropDown(
            exchangeState = state,
            onFromChange = {
                shouldShowConvert.value = true
                exchangeViewModel.handleEvent(OnFromChange(it))
            }
        )

        SwapButton(
            onClick = {
                // swap from and to values in UI // TODO
                exchangeViewModel.handleEvent(ExchangeViewEvent.OnSwapClick)
            })

        ToSection()

        ToDropDown(
            exchangeState = state,
            onToChange = {
                selectedToValue.value = it
                shouldShowConvert.value = true
                exchangeViewModel.handleEvent(OnToChange(it))
            }
        )

        AmountSection()

        AmountField(
            selectedToValue = selectedToValue,
            onAmountChange = {
                shouldShowConvert.value = true
                exchangeViewModel.handleEvent(OnAmountChange(it))
            },
            countryCode = toCountryCode
        )

        Spacer(modifier = Modifier.height(16.dp))

        ResultText(
            result = convertResult,
            currency = toCountryCode
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (shouldShowConvert.value) {
            ConvertButton(
                onConvertClicked = {
                    keyboardController?.hide()
                    shouldShowConvert.value = false
                    exchangeViewModel.handleEvent(OnConvertClick)
                },
                isEnabled = amount.isNotEmpty()
            )
        }
    }
}


