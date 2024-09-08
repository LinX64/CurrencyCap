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
import androidx.compose.ui.text.font.FontWeight
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.trading_volume
import org.jetbrains.compose.resources.stringResource
import ui.components.base.CenteredColumn
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Error
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.components.tabs.components.CryptoGridItem
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun TopGainers(
    state: OverviewState,
    onNewsItemClick: (url: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(end = SPACER_PADDING_32)
    ) {
        Text(
            text = stringResource(Res.string.trading_volume),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            when (state) {
                Loading -> item { CenteredColumn { CircularProgressIndicator() } }
                is Success -> {
                    val topGainers = state.combinedRates.crypto.filter { it.high24h > 0 }.take(2)
                    items(topGainers.size) { index ->
                        CryptoGridItem(
                            cryptoItem = topGainers[index],
                            onCryptoItemClick = { _, _ -> }
                        )
                    }
                }

                is Error -> {
                    item {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}