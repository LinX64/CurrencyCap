package com.client.currencycap

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.screens.overview.OverviewScreen

@Composable
@Preview(
    showBackground = true, device = "spec:width=1080px,height=2220px,dpi=440",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }
    KoinPreview {
        OverviewScreen(
            padding = PaddingValues(16.dp),
            hazeState = hazeState,
        )
    }
}

