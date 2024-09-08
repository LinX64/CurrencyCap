package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.top_gainers
import org.jetbrains.compose.resources.stringResource
import ui.components.base.CenteredColumn
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.components.tabs.components.CryptoGridItem
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun TopGainers(
    state: OverviewState,
    onCryptoItemClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(end = SPACER_PADDING_32)
    ) {
        Text(
            text = stringResource(Res.string.top_gainers),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().height(220.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
        ) {
            when (state) {
                is Success -> {
                    val cryptoRates = state.combinedRates.crypto.filter { it.high24h > 0 }
                        .sortedByDescending { it.high24h }.take(4)
                    items(cryptoRates.size) { item ->
                        val cryptoItem = cryptoRates[item]
                        CryptoGridItem(
                            cryptoItem = cryptoItem,
                            onCryptoItemClick = onCryptoItemClick
                        )
                    }
                }

                is Loading -> item {
                    CenteredColumn {
                        CircularProgressIndicator()
                    }
                }

                else -> Unit
            }
        }
    }
}