package ui.screens.main.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import currencycap.composeapp.generated.resources.Res
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun AboutUsSection(
    modifier: Modifier = Modifier
) {
    var htmlContent by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        htmlContent = Res.readBytes("files/about_us.html").decodeToString()
    }

    val encodedHtml = UrlEncoderUtil.encode(htmlContent ?: "")
    val dataUrl = "data:text/html;charset=utf-8,$encodedHtml"
    val state = rememberWebViewState(url = dataUrl)

    Column {
        WebView(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = SPACER_PADDING_8, horizontal = SPACER_PADDING_16),
            state = state
        )
    }
}