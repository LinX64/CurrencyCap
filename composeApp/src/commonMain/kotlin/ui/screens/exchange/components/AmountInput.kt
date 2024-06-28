package ui.screens.exchange.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.common.DecimalFormat
import util.formatDecimalSeparator

@Composable
internal fun AmountInput(
    maxLength: Int = 10,
    onAmountChange: (String) -> Unit,
    onErrorMessage: (String) -> Unit,
    amount: String
) {
    val formattedAmount = if (amount.isNotEmpty()) {
        DecimalFormat().format(amount.toDouble()).formatDecimalSeparator()
    } else "0"

    TextField(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(35.dp))
            .animateContentSize(),
        value = formattedAmount,
        onValueChange = {
            if (it.isNotEmpty() && it.length <= maxLength) {
                onAmountChange(it)
            } else onErrorMessage("Invalid amount!")
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
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        )
    )
}

