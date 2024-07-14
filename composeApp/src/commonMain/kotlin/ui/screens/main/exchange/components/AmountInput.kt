package ui.screens.main.exchange.components

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
import util.AmountVisualTransformation

@Composable
internal fun AmountInput(
    amount: String,
    onAmountChange: (String) -> Unit,
    onErrorMessage: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(35.dp))
            .animateContentSize(),
        value = amount,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.matches(Regex("^\\d*.?\\d*$"))) {
                onAmountChange(newValue)
            } else onErrorMessage("Invalid amount format")
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        visualTransformation = AmountVisualTransformation()
    )
}
