package com.client.currencycap.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import domain.model.RateDao
import ui.components.BulbBackground
import ui.screens.components.RateItem
import ui.theme.AppM3Theme

@Preview
@Composable
private fun RateItemPreview() {
    AppM3Theme(dark = false) {
        BulbBackground {
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp),
                rows = GridCells.Fixed(1),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(5) { index ->
                    RateItem(
                        rate = RateDao(
                            code = "USD",
                            sell = 1,
                            buy = 1
                        )
                    )
                }
            }
        }
    }
}