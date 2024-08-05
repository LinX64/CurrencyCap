package ui.screens.main.exchange.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.last_update_text
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.screens.main.exchange.ExchangeUiState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.currencyConverterAnimation
import util.exitTransition
import util.formatTimestamp
import util.normalizeRateUsd

@Composable
internal fun ResultCard(
    uiState: ExchangeUiState,
    amount: String,
    onRefreshClick: () -> Unit
) {
    val animatedExchangeAmount by animateValueAsState(
        targetValue = uiState.convertedAmount,
        animationSpec = tween(300),
        typeConverter = currencyConverterAnimation, label = ""
    )

    AnimatedVisibility(
        visible = amount.isNotEmpty(),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + exitTransition()
    ) {
        GlassCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACER_PADDING_16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConvertedAmountRow(animatedExchangeAmount, uiState)

                Spacer(modifier = Modifier.height(SPACER_PADDING_8))

                val normalizedAmount = uiState.targetCurrency?.let { normalizeRateUsd(it) }
                val formattedAmount = normalizedAmount?.let { formatToPrice(it) } ?: "0.00"

                Text(
                    text = "1 ${uiState.sourceCurrency?.code} = $formattedAmount ${uiState.targetCurrency?.code}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(SPACER_PADDING_8))

                ExchangeRateRefreshComponent(onRefreshClick = onRefreshClick)
            }
        }
    }
}

@Composable
private fun ExchangeRateRefreshComponent(
    modifier: Modifier = Modifier,
    onRefreshClick: () -> Unit,
) {
    val currentTimestamp = Clock.System.now().toEpochMilliseconds()
    var lastUpdateTime by remember { mutableLongStateOf(currentTimestamp) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.last_update_text, formatTimestamp(lastUpdateTime)),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        AnimatedRefreshIcon(
            onClick = {
                lastUpdateTime = currentTimestamp
                onRefreshClick()
            }
        )
    }
}

@Composable
private fun AnimatedRefreshIcon(
    onClick: () -> Unit,
) {
    var isAnimating by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isAnimating) 360f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "Refresh Icon Rotation"
    )
    val coroutineScope = rememberCoroutineScope()

    IconButton(
        onClick = {
            coroutineScope.launch {
                isAnimating = true
                onClick()
                delay(1000)
                isAnimating = false
            }
        }
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Refresh exchange rate",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.rotate(rotation)
        )
    }
}

@Composable
private fun ConvertedAmountRow(
    animatedExchangeAmount: Double,
    uiState: ExchangeUiState
) {
    Row {
        Text(
            text = "${(animatedExchangeAmount * 100).toLong() / 100.0}",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(SPACER_PADDING_8))

        Text(
            text = uiState.targetCurrency?.code.toString(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}