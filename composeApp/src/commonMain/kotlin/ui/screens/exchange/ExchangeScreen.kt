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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import di.koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.BlurColumn
import ui.screens.exchange.ExchangeViewEvent.OnAmountChange
import ui.screens.exchange.ExchangeViewEvent.OnFromChange
import ui.screens.exchange.ExchangeViewEvent.OnToChange
import ui.screens.exchange.component.AmountField
import ui.screens.exchange.component.AmountSection
import ui.screens.exchange.component.ConvertButton
import ui.screens.exchange.component.FromDropDown
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
    val selectedCountryCode by exchangeViewModel.fromValue.collectAsState()
    val convertResult by exchangeViewModel.convertedResult.collectAsState()
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
                selectedCountryCode = selectedCountryCode,
                convertResult = convertResult,
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
    selectedCountryCode: String,
    convertResult: String,
    amount: String
) {
    BlurColumn(
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = modifier.padding(32.dp),
        ) {
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
                countryCode = selectedCountryCode
            )

            Spacer(modifier = Modifier.height(16.dp))

            ResultText(
                result = convertResult,
                currency = selectedCountryCode
            )

            Spacer(modifier = Modifier.height(16.dp))

            ConvertButton(
                onConvertClicked = { exchangeViewModel.handleEvent(ExchangeViewEvent.OnConvertClick) },
                isEnabled = amount.isNotEmpty()
            )
        }
    }
}

@Composable
private fun Disclaimer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "Disclaimer",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "The exchange rates are indicative and subject to change as per market rates.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        HyperlinkText()
    }
}

@Composable
private fun HyperlinkText() {
    val annotatedString = buildAnnotatedString {
        append("All rates are provided by CoinCap.io. For more information, ")
        pushStringAnnotation(tag = "URL", annotation = "https://coincap.io")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            append("visit CoinCap.io.")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    // Handle the URL click here, for example, open a web browser
                    println("Clicked URL: ${annotation.item}")
                }
        }
    )
}