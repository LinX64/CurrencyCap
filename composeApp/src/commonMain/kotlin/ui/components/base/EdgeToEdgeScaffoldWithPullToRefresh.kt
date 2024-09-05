package ui.components.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.navigation.util.ScreenRoutes.OVERVIEW

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EdgeToEdgeScaffoldWithPullToRefresh(
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    currentDestination: String?,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    bottomSheets: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val isOverviewScreen = currentDestination == OVERVIEW

    val pullToRefreshModifier = if (isOverviewScreen) Modifier.pullToRefresh(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh() }
    ) else Modifier

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        containerColor = containerColor,
        contentColor = contentColor,
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .then(pullToRefreshModifier),
            contentAlignment = Alignment.TopCenter
        ) {
            content(paddingValues)

            if (isOverviewScreen) {
                PullToRefreshDefaults.Indicator(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }

    bottomSheets()
}

