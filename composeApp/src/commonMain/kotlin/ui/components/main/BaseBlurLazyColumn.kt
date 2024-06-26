package ui.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze

@Composable
internal fun BaseBlurLazyColumn(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    hazeState: HazeState,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
            .padding(horizontal = 16.dp)
            .haze(
                state = hazeState,
                style = HazeStyle(
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    blurRadius = 35.dp,
                    noiseFactor = HazeDefaults.noiseFactor
                )
            ),
        contentPadding = padding,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        content()
    }
}