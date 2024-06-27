package ui.screens.exchange.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import data.model.exchange.AmountInputType
import data.model.exchange.CurrencyCode
import org.jetbrains.compose.resources.painterResource
import ui.common.DecimalFormat
import ui.common.formatDecimalSeparator

@Composable
internal fun AmountInput(
    amountInputType: AmountInputType,
    maxLength: Int = 12,
    selectedCode: CurrencyCode,
    onAmountChange: (String) -> Unit
) {
    var amountValue by rememberSaveable { mutableStateOf("") }
    val formattedAmount = if (amountValue.isNotEmpty()) {
        DecimalFormat().format(amountValue.toDouble()).formatDecimalSeparator()
    } else ""

    TextField(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(35.dp))
            .animateContentSize(),
        value = formattedAmount,
        onValueChange = {
            if (it.isNotEmpty()) {
                amountValue = it
                onAmountChange(it)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            errorContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(selectedCode.flag),
                contentDescription = null
            )
        }
    )
}

