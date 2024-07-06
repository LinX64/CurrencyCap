package ui.screens.initial.privacy_policy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLFile

@Composable
internal fun PrivacyPolicySection(
    modifier: Modifier = Modifier
) {
    val state = rememberWebViewStateWithHTMLFile(
        fileName = "privacy_policy.html"
    )

    Column {
        val loadingState = state.loadingState
        if (loadingState is LoadingState.Loading) {
            LinearProgressIndicator(
                progress = { loadingState.progress },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        WebView(
            modifier = modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onSurface),
            state = state
        )
    }
}


