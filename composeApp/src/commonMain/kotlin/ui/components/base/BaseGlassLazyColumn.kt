package ui.components.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_96

@Composable
internal fun BaseGlassLazyColumn(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    contentPadding: PaddingValues = PaddingValues(SPACER_PADDING_16),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    isEmpty: Boolean = false,
    emptyContent: @Composable () -> Unit = {},
    content: LazyListScope.() -> Unit
) {
    val scrollableState = rememberLazyListState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .haze(
                    state = hazeState,
                    style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface),
                ),
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            state = scrollableState
        ) {
            content()

            item {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = SPACER_PADDING_96)
                ) {}
            }
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

