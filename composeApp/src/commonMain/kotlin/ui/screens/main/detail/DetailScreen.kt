package ui.screens.main.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import domain.model.main.Crypto
import org.koin.core.parameter.parametersOf
import ui.common.formatToPrice
import ui.components.GlassCard
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.profile.components.HelpCenterBaseGlassCard
import ui.theme.colors.CurrencyColors

@Composable
internal fun DetailRoute(
    padding: PaddingValues,
    symbol: String,
    detailViewModel: DetailViewModel = koinViewModel { parametersOf(symbol) },
    hazeState: HazeState,
) {
    val state by detailViewModel.viewState.collectAsStateWithLifecycle()
    DetailScreen(
        state = state,
        hazeState = hazeState,
        padding = padding,
    )
}

@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    padding: PaddingValues,
    hazeState: HazeState,
) {
    val isLoading = state is DetailState.Loading

    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (state) {
            is DetailState.Success -> {
                val crypto = state.crypto

                item { DetailHeader(crypto, isLoading) }
                item { DetailBody(crypto, isLoading) }
                item { DescriptionCard(crypto, isLoading) }
            }

            else -> Unit
        }
    }
}

@Composable
private fun DescriptionCard(
    crypto: Crypto,
    loading: Boolean
) {
    HelpCenterBaseGlassCard(
        title = "Description",
        badgeColor = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }
    }
}

@Composable
private fun DetailHeader(crypto: Crypto, isLoading: Boolean) {
    GlassCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                model = crypto.image,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = crypto.name + " (" + crypto.symbol.uppercase() + ")",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "$${formatToPrice(crypto.currentPrice)}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DetailBody(
    crypto: Crypto,
    isLoading: Boolean
) {
    HelpCenterBaseGlassCard(title = "Stats") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Market Cap",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Text(
                    text = formatToPrice(crypto.marketCap.toDouble()),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Volume",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                val textColor =
                    if (crypto.priceChangePercentage24h > 0) CurrencyColors.Green.primary else CurrencyColors.Red.primary
                Text(
                    text = "${crypto.priceChangePercentage24h}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "24h High",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Text(
                    text = formatToPrice(crypto.high24h),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "24h Low",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Text(
                    text = formatToPrice(crypto.low24h),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "All Time High",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Text(
                    text = formatToPrice(crypto.ath),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}