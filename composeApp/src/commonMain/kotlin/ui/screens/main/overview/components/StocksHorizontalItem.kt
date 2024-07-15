package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_48
import domain.model.main.Rate
import org.jetbrains.compose.resources.painterResource
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.ICON_SIZE_48
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors

@Composable
internal fun StocksHorizontalItem(
    modifier: Modifier = Modifier,
    icon: String,
    rate: Rate,
    isLoading: Boolean = false
) {
    GlassCard {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(CARD_CORNER_RADIUS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLoading) {
                ItemPlaceHolder(modifier = Modifier.size(ICON_SIZE_48))
            } else {
                AsyncImage(
                    modifier = Modifier.size(ICON_SIZE_48),
                    model = icon,
                    contentDescription = null,
                    placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                    error = painterResource(Res.drawable.baseline_monetization_on_48)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FirstColumn(rate = rate, isLoading = isLoading)
                EndHorizontalComponent(isLoading = isLoading)
            }
        }
    }
}

@Composable
private fun FirstColumn(
    modifier: Modifier = Modifier,
    rate: Rate,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier.padding(start = SPACER_PADDING_8)
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = rate.symbol,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        val formattedRate = if (rate.rateUsd.isNotBlank()) formatToPrice(rate.rateUsd.toDouble()) else ""
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = "$$formattedRate",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EndHorizontalComponent(
    isLoading: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "$898.5",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                // TODO: add upward arrow icon and downward arrow icon
                Icon(
                    modifier = if (isLoading) getPlaceHolder(Modifier.size(CARD_CORNER_RADIUS)) else Modifier,
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = null,
                    tint = CurrencyColors.Text_Green
                )

                Text(
                    modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                    text = "%22.5",
                    color = CurrencyColors.Text_Green,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}