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
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
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
import ui.screens.main.overview.components.getPlaceHolder
import ui.screens.main.profile.components.HelpCenterBaseGlassCard
import ui.theme.colors.CurrencyColors

@Composable
internal fun DetailRoute(
    padding: PaddingValues,
    hazeState: HazeState,
    symbol: String,
    detailViewModel: DetailViewModel = koinViewModel { parametersOf(symbol) }
) {
    val state by detailViewModel.viewState.collectAsStateWithLifecycle()
    DetailScreen(
        state = state,
        padding = padding,
        hazeState = hazeState
    )
}

@Composable
internal fun DetailScreen(
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
                item { DetailBody(crypto) }
                //item { DescriptionCard(description, isLoading) } TODO: fix this
            }

            else -> Unit
        }
    }
}

@Composable
private fun DetailHeader(
    crypto: Crypto,
    isLoading: Boolean
) {
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
            val isLoadingModifier = if (isLoading) getPlaceHolder(
                Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            ) else Modifier
                .size(100.dp)
                .clip(CircleShape)

            AsyncImage(
                modifier = isLoadingModifier,
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
    crypto: Crypto
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

@Composable
private fun DescriptionCard(
    description: String,
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
        ) {
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = Justify,
                maxLines = 24
            )
        }
    }
}