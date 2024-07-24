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
import currencycap.composeapp.generated.resources.rate
import domain.model.main.BonbastRate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.theme.AppDimensions.ICON_SIZE_48
import ui.theme.AppDimensions.SPACER_PADDING_16
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
                .padding(vertical = SPACER_PADDING_16, horizontal = SPACER_PADDING_16)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8)
        ) {
            if (isLoading) {
                ItemPlaceHolder(modifier = Modifier.size(ICON_SIZE_48))
            } else {
                AsyncImage(
                    modifier = Modifier.size(ICON_SIZE_48),
                    placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                    error = painterResource(Res.drawable.baseline_monetization_on_48),
                    model = icon,
                    contentDescription = stringResource(Res.string.rate)
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
