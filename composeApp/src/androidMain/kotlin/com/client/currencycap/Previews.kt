package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import domain.model.DataDao
import ui.components.BlurDarkBackground
import ui.screens.main.components.RateHorizontalItem
import ui.theme.AppM3Theme

@Preview
@Composable
private fun RateItemPreview() {
    AppM3Theme(dark = false) {
        BlurDarkBackground {
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .widthIn(max = 250.dp, min = 250.dp),
                rows = GridCells.Fixed(3),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) { index ->
                    RateHorizontalItem(
                        rate = DataDao(
                            currencySymbol = "USD",
                            symbol = "USD",
                            id = "USD",
                            type = "USD",
                            rateUsd = "44422",
                        ),
                        icon = "https://www.google.com"
                    )
                }
            }
        }
    }
}