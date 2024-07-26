package ui.components.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun BaseGlassLazyColumn(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    hazeState: HazeState,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    isEmpty: Boolean = false,
    emptyContent: @Composable () -> Unit = {},
    content: LazyListScope.() -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
                .haze(
                    state = hazeState,
                    style = HazeStyle(
                        tint = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                        blurRadius = SPACER_PADDING_16
                    )
                ),
            contentPadding = padding,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content()
        }

        if (isEmpty) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                emptyContent()
            }
        }
    }
}

