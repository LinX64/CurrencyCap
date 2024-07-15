package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import domain.model.main.BonbastRate
import org.jetbrains.compose.resources.painterResource
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun RateItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    icon: String,
    rate: BonbastRate
) {
    GlassCard {
        Column(
            modifier = modifier
                .padding(vertical = CARD_CORNER_RADIUS, horizontal = CARD_CORNER_RADIUS)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8)
        ) {
            if (isLoading) {
                ItemPlaceHolder(modifier = Modifier.size(48.dp))
            } else {
                AsyncImage(
                    modifier = Modifier.size(48.dp),
                    placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                    error = painterResource(Res.drawable.baseline_monetization_on_48),
                    model = icon,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = rate.code,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
                style = MaterialTheme.typography.bodyLarge,
            )

            val formattedPrice = formatToPrice(rate.sell)
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "$formattedPrice t",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
