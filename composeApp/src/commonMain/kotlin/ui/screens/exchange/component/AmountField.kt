package ui.screens.exchange.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ui.common.getCountryFlag

@Composable
internal fun AmountField(
    modifier: Modifier = Modifier,
    maxLength: Int = 12,
    countryCode: String,
    onAmountChange: (String) -> Unit,
) {
    var amount by remember { mutableStateOf("") }
    val containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = amount,
        onValueChange = {
            if (it.isEmpty() || it.length <= maxLength) {
                amount = it
                onAmountChange(it)
            }
        },
        placeholder = { if (amount.isEmpty()) Text(text = "enter amount") },
        leadingIcon = { LeadingIcon(countryCode) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        visualTransformation = CurrencyVisualTransformation(countryCode)
    )
}

@Composable
private fun LeadingIcon(countryCode: String) {
    val flag = if (countryCode.isEmpty()) "AE".getCountryFlag() else countryCode.getCountryFlag()
    Text(text = flag)
}
