package ui.screens.main.exchange

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.exchange_illustration
import data.local.model.exchange.CurrencyType
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.GlassCard
import ui.components.HandleNavigationEffect
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.exchange.ExchangeViewEvent.OnConvertClicked
import ui.screens.main.exchange.ExchangeViewEvent.OnSwitchCurrencies
import ui.screens.main.exchange.components.AmountInput
import ui.screens.main.exchange.components.CurrencyInputs
import ui.screens.main.exchange.components.CurrencyPicker
import ui.screens.main.exchange.components.Disclaimer
import util.exitTransition

private const val DEFAULT_VALUE = "100"

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
    var amount by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (amount.isEmpty()) {
            amount = DEFAULT_VALUE
        }
    }

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
                    amount = amount,
                    onErrorMessage = {
                        keyboardController?.hide()
                        onError(it)
                    },
                    onAmountChange = {
                        amount = it
                        viewModel.handleEvent(OnConvertClicked(amount))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                thickness = 1.dp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = amount.isNotEmpty() && amount != DEFAULT_VALUE,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + exitTransition()
        ) {
            GlassCard { ResultCard(state = state) }
        }

        Disclaimer()
    }
}

@Composable
private fun ResultCard(
    state: ExchangeState.ExchangeUiState
) {
    val convertedAmount = state.convertedAmount
    val currencyConverterAnimation = object : TwoWayConverter<Double, AnimationVector1D> {
        override val convertFromVector: (AnimationVector1D) -> Double = { vector ->
            vector.value.toDouble()
        }

        override val convertToVector: (Double) -> AnimationVector1D = { value ->
            AnimationVector1D(value.toFloat())
        }
    }

    val animatedExchangeAmount by animateValueAsState(
        targetValue = convertedAmount,
        animationSpec = tween(300),
        typeConverter = currencyConverterAnimation
    )

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "${(animatedExchangeAmount * 100).toLong() / 100.0}",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = state.targetCurrency?.code.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


