package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import ui.screens.overview.OverviewState
import ui.screens.overview.components.getPlaceHolder

@Composable
internal fun SearchViewHeader(
    state: OverviewState
) {
    val hazeState = remember { HazeState() }
    val isLoading = state is OverviewState.Loading

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                modifier = if (isLoading) getPlaceHolder(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .haze(hazeState)
                        .padding(16.dp)
                ) else Modifier
                    .fillMaxWidth(0.8f)
                    .haze(hazeState)
                    .padding(16.dp),
                shape = RoundedCornerShape(25.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Icon(
                        modifier = if (isLoading) getPlaceHolder(Modifier.padding(16.dp)) else Modifier.padding(16.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )

                    Text(
                        modifier = if (isLoading) getPlaceHolder(Modifier.padding(16.dp)) else Modifier.padding(16.dp),
                        text = "Type to search",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            Card(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(35.dp),
            ) {
                IconButton(
                    modifier = if (isLoading) getPlaceHolder(Modifier.size(64.dp)) else Modifier.size(64.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}