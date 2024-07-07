package ui.screens.initial.privacy_policy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import currencycap.composeapp.generated.resources.Res
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun PrivacyPolicySection(
    modifier: Modifier = Modifier
) {
    var htmlContent by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        htmlContent = Res.readBytes("files/privacy_policy.html").decodeToString()
    }

    val encodedHtml = UrlEncoderUtil.encode(htmlContent ?: "")
    val dataUrl = "data:text/html;charset=utf-8,$encodedHtml"
    val state = rememberWebViewState(url = dataUrl)

    Column {
        val loadingState = state.loadingState
        if (loadingState is LoadingState.Loading) {
            CircularProgressIndicator(progress = { loadingState.progress })
        }

        WebView(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            state = state
        )
    }
}


