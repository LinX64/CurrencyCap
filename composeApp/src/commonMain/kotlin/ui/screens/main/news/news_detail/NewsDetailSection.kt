package ui.screens.main.news.news_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun NewsDetailSection(
    url: String,
    modifier: Modifier = Modifier
) {
    val pageUrl = remember { mutableStateOf(url) }
    val state = rememberWebViewState(pageUrl.value)

    Column {
        WebView(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = SPACER_PADDING_8, horizontal = SPACER_PADDING_16),
            state = state
        )
    }
}