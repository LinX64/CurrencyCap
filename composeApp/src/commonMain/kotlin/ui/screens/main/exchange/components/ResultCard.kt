package ui.screens.main.exchange.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import ui.common.formatToPrice
import ui.components.base.GlassCard
import ui.screens.main.exchange.ExchangeUiState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.currencyConverterAnimation
import util.exitTransition
import util.normalizeRateUsd

@Composable
internal fun ResultCard(
    uiState: ExchangeUiState,
    amount: String
) {
    val animatedExchangeAmount by animateValueAsState(
        targetValue = uiState.convertedAmount,
        animationSpec = tween(300),
        typeConverter = currencyConverterAnimation
    )

    AnimatedVisibility(
        visible = amount.isNotEmpty(),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + exitTransition()
    ) {
        GlassCard {
            Column(
                modifier = Modifier.fillMaxWidth().padding(SPACER_PADDING_16),
                horizontalAlignment = Alignment.CenterHorizontally
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

                Spacer(modifier = Modifier.height(SPACER_PADDING_8))

                val normalizedAmount = uiState.targetCurrency?.let { normalizeRateUsd(it) }
                val formattedAmount = normalizedAmount?.let { formatToPrice(it) } ?: "0.00"

                Text(
                    text = "1 ${uiState.sourceCurrency?.code} = $formattedAmount ${uiState.targetCurrency?.code}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
