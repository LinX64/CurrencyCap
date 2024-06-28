package ui.screens.search.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_48
import domain.model.RateDto
import org.jetbrains.compose.resources.painterResource
import ui.common.formatToPrice
import ui.components.GlassCard
import ui.screens.overview.components.ItemPlaceHolder
import ui.screens.overview.components.getPlaceHolder
import ui.theme.colors.CurrencyColors
import util.getIconBy

@Composable
internal fun SearchItem(
    modifier: Modifier = Modifier,
    rate: RateDto,
    isLoading: Boolean = false
) {
    GlassCard {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLoading) {
                ItemPlaceHolder(modifier = Modifier.size(48.dp))
            } else {
                AsyncImage(
                    modifier = Modifier.size(48.dp),
                    model = getIconBy(rate.symbol),
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
    rate: RateDto,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier.padding(start = 8.dp)
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = rate.symbol,
            color = Color.White,
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
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                // TODO: add upward arrow icon and downward arrow icon
                Icon(
                    modifier = if (isLoading) getPlaceHolder(Modifier.size(16.dp)) else Modifier,
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