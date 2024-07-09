package ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_letters_ai
import currencycap.composeapp.generated.resources.ic_search_normal
import org.jetbrains.compose.resources.painterResource
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.components.getPlaceHolder

@Composable
internal fun SearchViewHeader(
    state: OverviewState,
    onSearchCardClicked: () -> Unit,
    onCircleButtonClicked: () -> Unit
) {
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
                        .padding(8.dp)
                ) else Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                shape = RoundedCornerShape(25.dp),
                onClick = onSearchCardClicked,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Icon(
                        modifier = if (isLoading) getPlaceHolder(Modifier.padding(16.dp)) else Modifier.padding(16.dp),
                        painter = painterResource(Res.drawable.ic_search_normal),
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

            CircleButton(isLoading = isLoading, onCircleButtonClicked = onCircleButtonClicked)
        }
    }
}

@Composable
private fun CircleButton(
    isLoading: Boolean = false,
    onCircleButtonClicked: () -> Unit
) {
    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(35.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
        )
    ) {
        IconButton(
            modifier = if (isLoading) getPlaceHolder(Modifier.size(64.dp)) else Modifier.size(64.dp),
            onClick = onCircleButtonClicked
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_letters_ai),
                contentDescription = "Info",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}