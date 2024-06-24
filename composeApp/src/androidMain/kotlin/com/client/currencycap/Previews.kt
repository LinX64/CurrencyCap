package com.client.currencycap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ui.components.BaseCenterColumn
import ui.screens.overview.OverviewState
import ui.screens.overview.components.WatchList

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun LoginScreenPreview() {
    KoinPreview {
        BaseCenterColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            WatchList(
                overviewState = OverviewState.Loading
            )
        }
    }
}

