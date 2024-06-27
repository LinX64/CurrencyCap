package com.client.currencycap

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import data.model.exchange.CurrencyType
import dev.chrisbanes.haze.HazeState
import ui.components.CenteredColumn
import ui.screens.exchange.components.CurrencyPicker
import ui.screens.exchange.dummyCurrencyList

@Composable
@Preview(
    showBackground = true, device = "spec:width=1080px,height=2220px,dpi=440",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }
    var selectedCurrencyType: CurrencyType by remember { mutableStateOf(CurrencyType.None) }

    KoinPreview {
        CenteredColumn {
            CurrencyPicker(
                currencyList = dummyCurrencyList(),
                currencyType = selectedCurrencyType,
                onEvent = {},
                onDismiss = { },
                hazeState = hazeState
            )
        }
    }
}

