package ui.screens.main.assets_live_price.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.AssetPriceItem
import kotlinx.coroutines.delay
import util.separateCamelCase

@Composable
internal fun AnimatedRateRow(
    rateItem: AssetPriceItem,
) {
    var previousPrice by rememberSaveable { mutableStateOf(rateItem.price) }
    val priceChange = rateItem.price.toDouble() - previousPrice.toDouble()

    val priceColor = when {
        priceChange > 0 -> Color(0xFF4CAF50)
        priceChange < 0 -> Color(0xFFE57373)
        else -> Color.Gray
    }

    var showAnimation by remember { mutableStateOf(false) }
    var currentColor by remember { mutableStateOf(Color.Gray) }

    val animatedColor by animateColorAsState(
        targetValue = if (showAnimation) priceColor else currentColor,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(rateItem.price) {
        if (rateItem.price != previousPrice) {
            showAnimation = true
            currentColor = priceColor
            delay(1000)
            showAnimation = false
            previousPrice = rateItem.price
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(
                color = if (showAnimation) priceColor.copy(alpha = 0.1f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rateItem.symbol.separateCamelCase().replace("-", " "),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$${rateItem.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = animatedColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = if (priceChange >= 0) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (priceChange >= 0) "Price increased" else "Price decreased",
                tint = currentColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}