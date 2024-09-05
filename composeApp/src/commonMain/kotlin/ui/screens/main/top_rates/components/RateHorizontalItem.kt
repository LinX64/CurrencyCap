package ui.screens.main.top_rates.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import currencycap.composeapp.generated.resources.rate
import domain.model.main.BonbastRate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.screens.main.overview.components.getPlaceHolder
import ui.theme.AppDimensions.ICON_SIZE_48
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun RateHorizontalItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    icon: String,
    rate: BonbastRate
) {
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SPACER_PADDING_16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = if (isLoading) getPlaceHolder(Modifier.size(ICON_SIZE_48)) else Modifier.size(
                    ICON_SIZE_48
                ),
                model = icon,
                contentDescription = stringResource(Res.string.rate),
                placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                error = painterResource(Res.drawable.baseline_monetization_on_48)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = rate.code,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier
                )

                val formattedPrice = formatToPrice(rate.sell)
                Text(
                    text = "$formattedPrice t",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier
                )
            }
        }
    }
}