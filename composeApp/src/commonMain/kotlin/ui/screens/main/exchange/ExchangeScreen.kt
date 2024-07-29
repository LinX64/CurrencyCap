package ui.screens.main.exchange

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.amount_text
import currencycap.composeapp.generated.resources.exchange
import currencycap.composeapp.generated.resources.exchange_illustration
import data.local.model.exchange.CurrencyType
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.components.base.BaseGlassLazyColumn
import ui.components.base.HandleNavigationEffect
import ui.screens.main.exchange.ExchangeNavigationEffect.ShowSnakeBar
import ui.screens.main.exchange.ExchangeViewEvent.OnConvert
import ui.screens.main.exchange.ExchangeViewEvent.OnSwitchCurrencies
import ui.screens.main.exchange.components.AmountInput
import ui.screens.main.exchange.components.CurrencyInputs
import ui.screens.main.exchange.components.CurrencyPicker
import ui.screens.main.exchange.components.Disclaimer
import ui.screens.main.exchange.components.ResultCard
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun ExchangeRoute(
    hazeState: HazeState,
    viewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>(),
    onError: (String) -> Unit,
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    ExchangeScreen(
        hazeState = hazeState,
        state = state,
        onError = onError,
        handleEvent = viewModel::handleEvent
    )

    HandleNavigationEffect(viewModel) { effect ->
        when (effect) {
            is ShowSnakeBar -> onError(effect.message)
        }
    }
}

@Composable
internal fun ExchangeScreen(
    hazeState: HazeState,
    onError: (String) -> Unit,
    state: ExchangeState,
    handleEvent: (ExchangeViewEvent) -> Unit
) {
    BaseGlassLazyColumn(
        hazeState = hazeState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ExchangeCard(
                uiState = state,
                hazeState = hazeState,
                onError = onError,
                handleEvent = handleEvent
            )
        }
    }
}

@Composable
private fun ExchangeCard(
    modifier: Modifier = Modifier,
    uiState: ExchangeState,
    hazeState: HazeState,
    onError: (String) -> Unit,
    handleEvent: (ExchangeViewEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = uiState as ExchangeUiState
    var dialogOpened by rememberSaveable { mutableStateOf(false) }
    var selectedCurrencyType: CurrencyType by remember { mutableStateOf(CurrencyType.None) }
    var amount by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (amount.isEmpty()) {
            amount = DEFAULT_VALUE
            handleEvent(OnConvert(DEFAULT_VALUE))
        }
    }

    Column {
        Column(
            modifier = modifier.padding(SPACER_PADDING_16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(SPACER_PADDING_16))

            Image(
                modifier = Modifier.size(84.dp),
                painter = painterResource(Res.drawable.exchange_illustration),
                contentDescription = stringResource(Res.string.exchange),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CurrencyInputs(
                source = state.sourceCurrency,
                target = state.targetCurrency,
                onSwitch = { handleEvent(OnSwitchCurrencies) },
                onCurrencyTypeSelect = {
                    dialogOpened = true
                    selectedCurrencyType = it
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = stringResource(Res.string.amount_text),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(4.dp))

            AmountInput(
                amount = amount,
                onAmountChange = {
                    amount = it
                    handleEvent(OnConvert(amount))
                },
                onErrorMessage = {
                    keyboardController?.hide()
                    onError(it)
                }
            )
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        Box(
            modifier = Modifier.fillMaxWidth()
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            VerticalDivider(
                modifier = Modifier.fillMaxHeight().width(1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                thickness = 1.dp
            )
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        ResultCard(uiState = state, amount = amount)

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        Disclaimer()
    }

    if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
        CurrencyPicker(
            hazeState = hazeState,
            currencyList = state.currencyRates,
            currencyType = selectedCurrencyType,
            onEvent = { event ->
                handleEvent(event)
                dialogOpened = false
                selectedCurrencyType = CurrencyType.None
            },
            onDismiss = {
                dialogOpened = false
                selectedCurrencyType = CurrencyType.None
            }
        )
    }
}

private const val DEFAULT_VALUE = "100"