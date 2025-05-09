package ui.screens.main.exchange.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.switch_ic
import data.local.model.exchange.CurrencyCode
import data.local.model.exchange.CurrencyType
import domain.model.CurrencyRate
import org.jetbrains.compose.resources.painterResource
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun CurrencyInputs(
    source: CurrencyRate?,
    target: CurrencyRate?,
    onSwitch: () -> Unit,
    onCurrencyTypeSelect: (CurrencyType) -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }
    val animatedRotation by animateFloatAsState(
        targetValue = if (animationStarted) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyView(
            placeholder = "From",
            currencyRate = source,
            onClick = {
                onCurrencyTypeSelect(
                    CurrencyType.Source(CurrencyCode.valueOf(it.code))
                )
            }
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        IconButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .rotate(animatedRotation)
                .graphicsLayer {
                    rotationY = animatedRotation
                },
            onClick = {
                animationStarted = !animationStarted
                onSwitch()
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        CurrencyView(
            placeholder = "To",
            currencyRate = target,
            onClick = {
                onCurrencyTypeSelect(
                    CurrencyType.Target(CurrencyCode.valueOf(it.code))
                )
            }
        )
    }
}