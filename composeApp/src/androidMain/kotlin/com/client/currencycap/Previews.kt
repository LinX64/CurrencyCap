package com.client.currencycap

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import data.model.exchange.CurrencyType
import data.model.news.Article
import dev.chrisbanes.haze.HazeState
import ui.components.main.BaseBlurLazyColumn
import ui.screens.news.news_detail.NewsDetailHeader
import ui.screens.news.news_detail.getDummyNewsItem

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }
    var selectedCurrencyType: CurrencyType by remember { mutableStateOf(CurrencyType.None) }

    KoinPreview {
        NewsDetail(
            padding = PaddingValues(0.dp),
            hazeState = hazeState
        )
    }
}

@Composable
fun NewsDetail(
    padding: PaddingValues,
    hazeState: HazeState,
    article: Article = getDummyNewsItem()
) {
    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding,
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            NewsDetailHeader(article = article)
        }
    }
}


