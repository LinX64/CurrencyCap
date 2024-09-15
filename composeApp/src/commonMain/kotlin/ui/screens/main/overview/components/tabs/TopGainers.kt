package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import ui.screens.main.overview.OverviewState.Idle
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.components.tabs.components.CryptoGridItem
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun TopGainers(
    state: OverviewState,
    onCryptoItemClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(Res.string.top_gainers),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(220.dp)
        ) {
            repeat(2) { rowIndex ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_8)
                ) {
                    repeat(2) { columnIndex ->
                        val index = rowIndex * 2 + columnIndex
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            when (state) {
                                is Success -> {
                                    val cryptoRates = state.combinedRates.crypto
                                        .filter { it.high24h > 0 }
                                        .sortedByDescending { it.high24h }
                                        .take(4)
                                    if (index < cryptoRates.size) {
                                        val cryptoItem = cryptoRates[index]
                                        CryptoGridItem(
                                            cryptoItem = cryptoItem,
                                            onCryptoItemClick = onCryptoItemClick
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(MaterialTheme.colorScheme.surface)
                                        )
                                    }
                                }

                                is Idle -> {
                                    CenteredColumn {
                                        CircularProgressIndicator()
                                    }
                                }

                                else -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(MaterialTheme.colorScheme.surface)
                                    )
                                }
                            }
                        }
                    }
                }
                if (rowIndex == 0) {
                    Spacer(modifier = Modifier.height(SPACER_PADDING_8))
                }
            }
        }
    }
}