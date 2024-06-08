package ui.screens.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import ui.screens.exchange.component.AmountField
import ui.screens.exchange.component.AmountSection
import ui.screens.exchange.component.ConvertButton
import ui.screens.exchange.component.FromDropDown
import ui.screens.exchange.component.Header
import ui.screens.exchange.component.ResultText
import ui.screens.exchange.component.ToDropDown
import ui.screens.exchange.component.ToSection

@Composable
fun ExchangeRoute(
    modifier: Modifier = Modifier,
    exchangeViewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>()
) {
    ExchangeScreen(
        modifier = modifier,
        onAmountChange = exchangeViewModel::onAmountChange,
        onFromChange = exchangeViewModel::onFromChange,
        onToChange = exchangeViewModel::onToChange,
        onConvertClick = exchangeViewModel::onConvertClick,
        convertResult = exchangeViewModel.convertResult.value
    )
}

@Composable
internal fun ExchangeScreen(
    modifier: Modifier = Modifier,
    onAmountChange: (String) -> Unit,
    onFromChange: (String) -> Unit,
    onToChange: (String) -> Unit,
    onConvertClick: () -> Unit,
    convertResult: String = ""
) {
    val amount by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenContent(
            modifier = modifier,
            onAmountChange = onAmountChange,
            onFromChange = onFromChange,
            onToChange = onToChange,
            amount = amount,
            convertResult = convertResult,
            onConvertClick = onConvertClick
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    onFromChange: (String) -> Unit,
    onToChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    amount: String,
    convertResult: String,
    onConvertClick: () -> Unit
) {
    val hazeState = remember { HazeState() }
    Box(
        modifier
            .padding(top = 50.dp)
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
                    onFromChange = { onFromChange(it) }
                )

                ToSection()

                ToDropDown(
                    onToChange = { onToChange(it) }
                )

                AmountSection()

                AmountField(
                    onAmountChange = { onAmountChange(it) },
                    onFromChanged = amount
                )

                Spacer(modifier = Modifier.height(32.dp))

                ResultText(result = convertResult)

                ConvertButton(onConvertClicked = onConvertClick)
            }
        }
    }
}
