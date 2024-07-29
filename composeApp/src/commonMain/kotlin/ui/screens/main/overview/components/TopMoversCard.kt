package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.main.Crypto
import kotlinx.collections.immutable.persistentListOf
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors

@Composable
internal fun TopMoversCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    topMovers: Crypto,
    onClick: (id: String, symbol: String) -> Unit,
) {
    GlassCard(
        modifier = modifier
            .padding(end = SPACER_PADDING_8),
        isClickable = true,
        onCardClick = { onClick(topMovers.id, topMovers.symbol) }
    ) {
        Column(
            modifier = Modifier.padding(SPACER_PADDING_16),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = topMovers.symbol,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(SPACER_PADDING_8))

            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = topMovers.name,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            val formattedPrice = formatToPrice(topMovers.currentPrice)
            val formattedCurrentPrice = "$${formattedPrice}"
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = formattedCurrentPrice,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(SPACER_PADDING_16))

            val priceData = persistentListOf(
                topMovers.low24h.toFloat(),
                topMovers.currentPrice.toFloat(),
                topMovers.high24h.toFloat()
            )

            TopMoversChart(
                modifier = if (isLoading) getPlaceHolder(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) else Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                list = priceData
            )

            Spacer(modifier = Modifier.height(SPACER_PADDING_32))

            BottomRow(
                topMovers = topMovers,
                isLoading = isLoading
            )
        }
    }
}

@Composable
private fun BottomRow(
    isLoading: Boolean,
    topMovers: Crypto
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val formattedPriceChange = formatToPrice(topMovers.priceChange24h)
        val formattedChange = "+${formattedPriceChange}%"
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = formattedChange,
            color = CurrencyColors.Green.primary,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
        )

        ChangeIcon(
            valueChange = topMovers.priceChange24h,
            isLoading = isLoading
        )
    }
}