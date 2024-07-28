package ui.screens.main.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.crypto_image
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import domain.model.main.Crypto
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import ui.common.formatToPrice
import ui.components.base.BaseGlassLazyColumn
import ui.components.base.GlassCard
import ui.components.main.SectionRowItem
import ui.screens.main.overview.components.ChangeIcon
import ui.screens.main.overview.components.TopMoversChart
import ui.screens.main.overview.components.getPlaceHolder
import ui.screens.main.profile.components.HelpCenterBaseGlassCard
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors
import util.getDummyCryptoItem

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
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_16)
    ) {
        if (state is DetailState.Success) {
            val crypto = state.crypto
            val description = "state.description"
            item { DetailHeader(crypto = crypto) }
            item { DetailBody(crypto = crypto) }
            item { DescriptionCard(description = description) }
        }

        if (isLoading) {
            val crypto = getDummyCryptoItem()
            val description = "state.description"

            item { DetailHeader(crypto, isLoading = true) }
            item { DetailBody(crypto = crypto, isLoading = true) }
            item { DescriptionCard(description = description, isLoading = false) }
        }
    }
}

@Composable
private fun DetailHeader(
    crypto: Crypto,
    isLoading: Boolean = false
) {
    var selectedChip by remember { mutableStateOf("24H") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlassCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACER_PADDING_16),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isLoadingModifier = if (isLoading) getPlaceHolder(
                        Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    ) else Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)

                    AsyncImage(
                        modifier = isLoadingModifier,
                        model = crypto.image,
                        contentDescription = stringResource(Res.string.crypto_image)
                    )

                    Column {
                        Text(
                            text = crypto.name + " (" + crypto.symbol.uppercase() + ")",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Market Cap: $${formatToPrice(crypto.marketCap.toDouble())}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(SPACER_PADDING_16))

                Text(
                    text = "$${formatToPrice(crypto.currentPrice)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    ChangeIcon(valueChange = crypto.priceChangePercentage24h, isLoading = isLoading)

                    Spacer(modifier = Modifier.width(SPACER_PADDING_8))

                    Text(
                        text = "${crypto.priceChangePercentage24h}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (crypto.priceChangePercentage24h > 0) CurrencyColors.Green.primary else CurrencyColors.Red.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(SPACER_PADDING_16))

                TopMoversChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    lightLineColor = CurrencyColors.Orange.primary,
                    lighterColor = CurrencyColors.Orange.primary.copy(alpha = 0.1f),
                    list = persistentListOf(
                        58.00f,
                        58.00f,
                        58.3f,
                        58.4f,
                        60.0f,
                        61.0f,
                        60.0f,
                    )
                )

                Spacer(modifier = Modifier.height(SPACER_PADDING_32))
            }
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        TimeRangeChips(
            selectedRange = selectedChip,
            onRangeSelected = { newRange ->
                selectedChip = newRange
            }
        )
    }
}

@Composable
private fun DetailBody(
    crypto: Crypto,
    isLoading: Boolean = false
) {
    val isLoadingModifier = if (isLoading) getPlaceHolder(Modifier) else Modifier
    Column {
        SectionRowItem(title = "About ${crypto.name}")

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

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
                        modifier = isLoadingModifier,
                        text = "Market Cap",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
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
                        modifier = isLoadingModifier,
                        text = "Volume",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    val textColor =
                        if (crypto.priceChangePercentage24h > 0) CurrencyColors.Green.primary else CurrencyColors.Red.primary
                    Text(
                        modifier = isLoadingModifier,
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
                        modifier = isLoadingModifier,
                        text = "24h High",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = "$${formatToPrice(crypto.high24h)}",
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
                        modifier = isLoadingModifier,
                        text = "24h Low",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = "$${formatToPrice(crypto.low24h)}",
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
                        modifier = isLoadingModifier,
                        text = "All Time High",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = "$${formatToPrice(crypto.ath)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DescriptionCard(
    description: String,
    isLoading: Boolean = false
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
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = description,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = Justify,
                maxLines = 24
            )
        }
    }
}

@Composable
private fun TimeRangeChips(
    timeRanges: ImmutableSet<String> = persistentSetOf("24H", "1W", "1M", "1Y", "All"),
    selectedRange: String,
    onRangeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        timeRanges.forEach { range ->
            AssistChip(
                onClick = { onRangeSelected(range) },
                label = {
                    Text(
                        text = range,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                shape = RoundedCornerShape(CARD_CORNER_RADIUS),
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (range == selectedRange)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surface,
                    labelColor = if (range == selectedRange)
                        MaterialTheme.colorScheme.surface
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}
