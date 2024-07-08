package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.screens.main.overview.components.tabs.NewsHomeItem
import util.getDummyNewsItem

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {
        Column {
            repeat(2) {
                NewsHomeItem(
                    onClick = { },
                    newsItem = getDummyNewsItem()
                )
            }
        }
    }
}
