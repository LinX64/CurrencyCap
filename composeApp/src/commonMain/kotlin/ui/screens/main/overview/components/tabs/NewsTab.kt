package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.components.CenteredColumn
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Error
import ui.screens.main.overview.OverviewState.Success

@Composable
internal fun NewsContent(state: OverviewState) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(end = 32.dp)
    ) {
        Text(
            text = "Latest News",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state is Error) {
                val message = state.message
                item {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            when (state) {
                OverviewState.Loading -> {
                    item { CenteredColumn { CircularProgressIndicator() } }
                }

                is Success -> {
                    val news = state.news
                    items(news.size) { index ->
                        NewsHomeItem(
                            onClick = { /*TODO*/ },
                            newsItem = state.news[index]
                        )
                    }
                }

                is Error -> {
                    val message = state.message
                    item {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}