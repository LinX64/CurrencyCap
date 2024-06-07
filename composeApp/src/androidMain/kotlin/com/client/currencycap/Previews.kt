package com.client.currencycap

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import domain.model.DataDao
import domain.model.RateDao
import ui.App
import ui.components.BlurBackground
import ui.components.NavigationItem
import ui.screens.main.CryptoState
import ui.screens.main.HomeScreen
import ui.screens.main.MainState
import ui.theme.AppM3Theme

val rates = CryptoState.Success(
    listOf(
        DataDao(
            currencySymbol = "USD",
            symbol = "USD",
            id = "USD",
            type = "USD",
            rateUsd = "44422",
        )
    )
)

@Composable
@Preview(showBackground = true)
private fun AppPreview() {
    AppM3Theme(dark = true) {
        BlurBackground {
            HomeScreen(
                rates = MainState.Success(
                    listOf(
                        RateDao(
                            code = "USD",
                            sell = 44422,
                            buy = 44422,
                        )
                    )
                ),
                cryptoRates = CryptoState.Success(
                    listOf(
                        DataDao(
                            currencySymbol = "USD",
                            symbol = "USD",
                            id = "USD",
                            type = "USD",
                            rateUsd = "44422",
                        )
                    )
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AppPreviewDark() {
    AppM3Theme(dark = true) {
        App()
    }
}

@Composable
@Preview(showBackground = true)
private fun AppPreviewLight() {
    AppM3Theme(dark = true) {

        val selectedIndex = 0
        val onItemClicked: (Int) -> Unit = {}

        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(50.dp))
        ) {
            NavigationItem(selectedIndex, onItemClicked)
        }
    }
}


