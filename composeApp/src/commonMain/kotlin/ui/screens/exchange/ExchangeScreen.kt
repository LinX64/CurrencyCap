package ui.screens.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import data.model.exchange.AmountInputType
import data.model.exchange.CurrencyType
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.BlurColumn
import ui.components.main.BaseBlurLazyColumn
import ui.screens.exchange.components.AmountInput
import ui.screens.exchange.components.CurrencyInputs
import ui.screens.exchange.components.CurrencyPicker
import ui.screens.exchange.components.Disclaimer

@Composable
@Preview
internal fun ExchangeScreen(
    exchangeViewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>(),
    padding: PaddingValues,
    onError: (String) -> Unit,
    hazeState: HazeState
) {
    val state by exchangeViewModel.state.collectAsStateWithLifecycle()
    val convertedResult by exchangeViewModel.convertedResult.collectAsState()
    val amount by exchangeViewModel.amountValue.collectAsState()

    BaseBlurLazyColumn(
        padding = padding,
        hazeState = hazeState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ExchangeCard(
                state = state,
                exchangeViewModel = exchangeViewModel,
                amount = amount
            )
        }
        item { Disclaimer() }
    }

//    HandleNavigationEffect(exchangeViewModel) { effect ->
//        when (effect) {
//            is ExchangeNavigationEffect.ShowSnakeBar -> onError(effect.message)
//        }
//    }
}

fun dummyCurrencyList(): List<Currency> = listOf(Currency("USD", 1.0), Currency("IRR", 1.0))

@Composable
private fun ExchangeCard(
    modifier: Modifier = Modifier,
    exchangeViewModel: ExchangeViewModel,
    state: HomeUiState,
    amount: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var dialogOpened by rememberSaveable { mutableStateOf(false) }
    var selectedCurrencyType: CurrencyType by remember { mutableStateOf(CurrencyType.None) }
    if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
        CurrencyPicker(
            currencyList = dummyCurrencyList(),
            currencyType = selectedCurrencyType,
            onEvent = {
                exchangeViewModel.onEvent(it)
                dialogOpened = false
                selectedCurrencyType = CurrencyType.None
            },
            onDismiss = {
                dialogOpened = false
                selectedCurrencyType = CurrencyType.None
            }
        )
    }

    BlurColumn(
        modifier = modifier.padding(24.dp)
    ) {
        CurrencyInputs(
            source = Currency("USD", 1.0),
            target = Currency("IRR", 1.0),
            onSwitch = {
                exchangeViewModel.onEvent(HomeEvent.SwitchCurrencies)
            },
            onCurrencyTypeSelect = {
                dialogOpened = true
                selectedCurrencyType = it
            }
        )
        Spacer(modifier = Modifier.height(12.dp))

        AmountInput(
            amountInputType = AmountInputType.SOURCE,
            amount = "100"
        )
        Spacer(modifier = Modifier.height(12.dp))

        AmountInput(
            amountInputType = AmountInputType.TARGET,
            amount = "100"
        )
    }
}


