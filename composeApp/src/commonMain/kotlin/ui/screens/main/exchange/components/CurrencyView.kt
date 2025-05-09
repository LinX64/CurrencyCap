package ui.screens.main.exchange.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.local.model.exchange.CurrencyCode
import domain.model.CurrencyRate
import org.jetbrains.compose.resources.painterResource
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_16
import util.enterTransition
import util.exitTransition
import util.transitionSpec

@Composable
fun RowScope.CurrencyView(
    placeholder: String,
    currencyRate: CurrencyRate?,
    onClick: (CurrencyRate) -> Unit
) {
    Column(
        modifier = Modifier.weight(1f)
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(size = CARD_CORNER_RADIUS))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                .height(64.dp)
                .clickable {
                    currencyRate?.let {
                        onClick(it)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = currencyRate != null,
                enter = enterTransition(),
                exit = exitTransition()
            ) {
                currencyRate?.let { data ->
                    AnimatedContent(
                        targetState = data,
                        transitionSpec = { transitionSpec() },
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(
                                    CurrencyCode.valueOf(data.code).flag
                                ),
                                tint = Color.Unspecified,
                                contentDescription = "Country Flag"
                            )

                            Spacer(modifier = Modifier.width(SPACER_PADDING_16))

                            Text(
                                text = CurrencyCode.valueOf(data.code).name,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}