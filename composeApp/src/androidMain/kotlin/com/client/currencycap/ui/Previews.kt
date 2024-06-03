package com.client.currencycap.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import domain.model.RateDao
import ui.components.BulbBackground
import ui.screens.components.RateItem
import ui.theme.AppM3Theme

@Preview
@Composable
fun RateDaoPreview() {
    AppM3Theme(dark = true) {
        BulbBackground {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10) {
                    RateItem(rate = RateDao("USD", buy = 58000, sell = 58000))
                }
            }
        }
    }
}
