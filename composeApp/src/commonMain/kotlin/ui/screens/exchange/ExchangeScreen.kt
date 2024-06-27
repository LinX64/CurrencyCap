package ui.screens.exchange

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.exchange_illustration
import data.model.exchange.AmountInputType
import data.model.exchange.CurrencyType
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.BlurColumn
import ui.components.main.BaseBlurLazyColumn
import ui.screens.exchange.components.AmountInput
import ui.screens.exchange.components.CurrencyInputs
import ui.screens.exchange.components.CurrencyPicker
import ui.screens.exchange.components.Disclaimer
import util.exitTransition

@Composable
@Preview
internal fun ExchangeScreen(
    exchangeViewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState,
    onError: (String) -> Unit,
) {
    val state by exchangeViewModel.state.collectAsStateWithLifecycle()

    BaseBlurLazyColumn(
        padding = padding,
        hazeState = hazeState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ExchangeCard(
                state = state,
                hazeState = hazeState,
                exchangeViewModel = exchangeViewModel,
                onError = onError
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
    hazeState: HazeState,
    onError: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var dialogOpened by rememberSaveable { mutableStateOf(false) }
    var selectedCurrencyType: CurrencyType by remember { mutableStateOf(CurrencyType.None) }

    if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
        CurrencyPicker(
            hazeState = hazeState,
            currencyList = dummyCurrencyList(),
            currencyType = selectedCurrencyType,
            onEvent = { event ->
                exchangeViewModel.onEvent(event)
                dialogOpened = false
                selectedCurrencyType = CurrencyType.None
            },
            onDismiss = {
                dialogOpened = false
                selectedCurrencyType = CurrencyType.None
            }
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BlurColumn {
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(84.dp),
                    painter = painterResource(Res.drawable.exchange_illustration),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(24.dp))

                CurrencyInputs(
                    source = Currency("USD", 1.0),
                    target = Currency("IRR", 1.0),
                    onSwitch = { exchangeViewModel.onEvent(HomeEvent.SwitchCurrencies) },
                    onCurrencyTypeSelect = {
                        dialogOpened = true
                        selectedCurrencyType = it
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                AmountInput(
                    amountInputType = AmountInputType.SOURCE,
                    onAmountChange = { exchangeViewModel.onEvent(HomeEvent.OnAmountChanged(it)) },
                    onErrorMessage = onError
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            VerticalDivider(
                modifier = Modifier
                    .width(1.dp)
                    .padding(horizontal = 16.dp),
                color = Color.Blue
            )
        } // check to see why this is not being shown

        AnimatedVisibility(
            visible = state.sourceCurrencyAmount.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + exitTransition()
        ) {
            BlurColumn {
                ResultCard(
                    state = state,
                    amount = state.sourceCurrencyAmount // TODO: pass the converted amount here
                )
            }
        }
    }
}

@Composable
internal fun ResultCard(
    state: HomeUiState,
    amount: String
) {
    val formattedAmountWithCode = "${state.targetCurrency?.code} $amount"
    val formattedAmount = "1 ${state.targetCurrency?.code} = ${state.sourceCurrencyAmount} ${state.sourceCurrency?.code}"

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formattedAmountWithCode,
            style = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formattedAmount,
            style = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        )
    }
}


