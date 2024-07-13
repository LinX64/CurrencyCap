package ui.screens.main.overview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.main.Crypto
import ui.common.formatToPrice
import ui.components.GlassCard
import ui.theme.colors.CurrencyColors

@Composable
internal fun TopMoversCard(
    isLoading: Boolean,
    topMovers: Crypto,
    onClick: (String) -> Unit
) {
    GlassCard(
        modifier = Modifier
            .padding(end = 8.dp)
            .wrapContentHeight(),
        isClickable = true,
        onCardClick = { onClick(topMovers.symbol) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .width(130.dp),
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

            Spacer(modifier = Modifier.height(8.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

            val changeData = listOf(
                topMovers.priceChange24h.toFloat(),
                topMovers.priceChangePercentage24h.toFloat()
            )

            val supplyData = listOf(
                topMovers.circulatingSupply.toFloat(),
                topMovers.totalSupply?.toFloat(),
                topMovers.maxSupply?.toFloat()
            )

            val priceData = listOf(
                topMovers.low24h.toFloat(),
                topMovers.currentPrice.toFloat(),
                topMovers.high24h.toFloat()
            )

            TopMoversChart(
                modifier = if (isLoading) getPlaceHolder(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .width(40.dp),
                ) else Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .width(40.dp),
                list = priceData
            )

            Spacer(modifier = Modifier.height(32.dp))

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
            isPositive = topMovers.priceChange24h > 0,
            isLoading = isLoading
        )
    }
}

@Composable
private fun MaterialsCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.then(modifier),
        shape = RoundedCornerShape(35.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Box(Modifier.padding(16.dp)) {
            content()
        }
    }
}