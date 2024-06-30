package ui.screens.main.exchange

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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.exchange_illustration
import data.local.model.exchange.AmountInputType
import data.local.model.exchange.CurrencyType
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.GlassCard
import ui.components.HandleNavigationEffect
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.exchange.ExchangeViewEvent.OnAmountValueChanged
import ui.screens.main.exchange.ExchangeViewEvent.OnSwitchCurrencies
import ui.screens.main.exchange.components.AmountInput
import ui.screens.main.exchange.components.CurrencyInputs
import ui.screens.main.exchange.components.CurrencyPicker
import ui.screens.main.exchange.components.Disclaimer
import ui.screens.main.exchange.components.ResultAmountInput
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

    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ExchangeCard(
                state = state,
                hazeState = hazeState,
                viewModel = exchangeViewModel,
                onError = onError
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
    viewModel: ExchangeViewModel,
    state: ExchangeState.ExchangeUiState,
    hazeState: HazeState,
    onError: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var dialogOpened by rememberSaveable { mutableStateOf(false) }
    var selectedCurrencyType: CurrencyType by remember { mutableStateOf(CurrencyType.None) }

    if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
        CurrencyPicker(
            hazeState = hazeState,
            currencyList = state.currencyRates,
            currencyType = selectedCurrencyType,
            onEvent = { event ->
                viewModel.handleEvent(event)
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
        GlassCard {
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    modifier = Modifier.size(84.dp),
                    painter = painterResource(Res.drawable.exchange_illustration),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )

                Spacer(modifier = Modifier.height(24.dp))

                CurrencyInputs(
                    source = state.sourceCurrency,
                    target = state.targetCurrency,
                    onSwitch = { viewModel.handleEvent(OnSwitchCurrencies) },
                    onCurrencyTypeSelect = {
                        dialogOpened = true
                        selectedCurrencyType = it
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                AmountInput(
                    onAmountChange = { viewModel.handleEvent(OnAmountValueChanged(it)) },
                    onErrorMessage = onError,
                    amount = state.targetCurrencyAmount
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
        } // TODO: check to see why this is not being shown

        AnimatedVisibility(
            visible = state.sourceCurrencyAmount.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + exitTransition()
        ) {
            GlassCard {
                ResultCard(
                    state = state,
                )
            }
        }
    }
}

@Composable
private fun ResultCard(
    state: ExchangeState.ExchangeUiState
) {
    val formattedAmount = "1 ${state.targetCurrency?.code} = ${state.sourceCurrencyAmount} ${state.sourceCurrency?.code}"

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResultAmountInput(
            amountInputType = AmountInputType.TARGET,
            amount = state.targetCurrencyAmount
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


