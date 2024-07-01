package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.components.CenteredColumn
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.components.PortfolioSection

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {
        CenteredColumn {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                PortfolioSection(
                    state = OverviewState.Loading,
                    hazeState = hazeState
                )
            }
        }
    }
}