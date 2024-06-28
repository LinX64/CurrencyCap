package ui.screens.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.components.main.BaseBlurLazyColumn

@Composable
internal fun BookmarksScreen(
    padding: PaddingValues,
    aiPredictViewModel: BookmarksViewModel = koinViewModel<BookmarksViewModel>(),
    hazeState: HazeState
) {
    val state by aiPredictViewModel.
    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding,
    ) {
        // if no bookmark, then show this

        item {
            NoBookmarks()
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun NoBookmarks() {
    var bytes by remember { mutableStateOf(ByteArray(0)) }
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(bytes.decodeToString()))

    LaunchedEffect(Unit) { bytes = Res.readBytes("files/no_bookmarks.json") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.padding(16.dp).size(150.dp),
            composition = composition
        )

        Text(
            text = "No bookmarks yet!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bookmark a coin or a news to see it here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}



