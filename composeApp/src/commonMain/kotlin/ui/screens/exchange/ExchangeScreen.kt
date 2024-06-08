package ui.screens.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import di.koinViewModel
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
internal fun ExchangeScreen(
    modifier: Modifier = Modifier,
    exchangeViewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>()
) {
    val state by exchangeViewModel.viewState.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenContent(
            exchangeViewModel = exchangeViewModel,
            state = state
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    exchangeViewModel: ExchangeViewModel,
    state: ExchangeState,
) {
    val hazeState = remember { HazeState() }
    val amount by remember { mutableStateOf("") }

    Box(
        modifier
            .fillMaxSize()
            .haze(
                state = hazeState,
                style = HazeDefaults.style(
                    tint = Color.White.copy(alpha = 0.1f),
                    blurRadius = 1.dp
                ),
            ),
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
                .align(Alignment.Center)
                .hazeChild(
                    state = hazeState,
                    shape = RoundedCornerShape(16.dp),
                )
        ) {
            Column(
                modifier = modifier.padding(16.dp)
                    .wrapContentHeight(),
            ) {
                Header()

                FromDropDown(
                    exchangeState = exchangeViewModel.viewState.value,
                    onFromChange = { exchangeViewModel.handleEvent(OnFromChange(it)) }
                )

                ToSection()

                ToDropDown(
                    exchangeState = exchangeViewModel.viewState.value,
                    onToChange = { exchangeViewModel.handleEvent(OnToChange(it)) }
                )

                AmountSection()

                AmountField(
                    onAmountChange = { exchangeViewModel.handleEvent(OnAmountChange(it)) },
                    onFromChanged = amount
                )

                Spacer(modifier = Modifier.height(32.dp))

                ResultText(result = exchangeViewModel.convertResult.value)

                Spacer(modifier = Modifier.height(32.dp))

                ConvertButton(onConvertClicked = { exchangeViewModel.handleEvent(OnConvertClick) })
            }
        }
    }
}
