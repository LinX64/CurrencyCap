package ui.screens.main.assets_live_price

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import domain.model.AssetPriceItem
import kotlinx.coroutines.delay
import ui.screens.main.overview.components.getPlaceHolder
import ui.theme.AppDimensions.SPACER_PADDING_8
import kotlin.math.abs

@Composable
internal fun AnimatedRateRow(
    rateItem: AssetPriceItem,
    previousPrice: String?,
    isLoading: Boolean = false
) {
    var showAnimation by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    val priceChange = previousPrice?.let { prev ->
        rateItem.price.toDouble() - prev.toDouble()
    }
    val changePercentage = priceChange?.let { change ->
        (change / previousPrice.toDouble()) * 100
    }

    val priceColor = when {
        priceChange == null -> MaterialTheme.colorScheme.onSurface
        priceChange > 0 -> Color.Green
        priceChange < 0 -> Color.Red
        else -> MaterialTheme.colorScheme.onSurface
    }

    val animatedColor by animateColorAsState(
        targetValue = if (showAnimation) priceColor else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    val shimmerEffect = rememberInfiniteTransition()
    val shimmerAlpha by shimmerEffect.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(rateItem.price) {
        showAnimation = true
        if (abs(priceChange ?: 0.0) > 0.01) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
        delay(1000)
        showAnimation = false
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SPACER_PADDING_8),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = rateItem.symbol.separateCamelCase(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            if (changePercentage != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (priceChange >= 0) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = if (priceChange >= 0) "Price increased" else "Price decreased",
                        tint = priceColor
                    )
                    Text(
                        text = abs(changePercentage).toString().take(5) + "%",
                        style = MaterialTheme.typography.bodySmall,
                        color = priceColor
                    )
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$${rateItem.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = animatedColor,
                modifier = Modifier.alpha(if (showAnimation) shimmerAlpha else 1f)
            )
            if (priceChange != null) {
                Icon(
                    imageVector = if (priceChange >= 0) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = if (priceChange >= 0) "Price increased" else "Price decreased",
                    tint = priceColor
                )
            }
        }
    }
}
