package ui.screens.main.exchange.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.local.model.exchange.AmountInputType
import ui.common.DecimalFormat
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.formatDecimalSeparator

@Composable
internal fun ResultAmountInput(
    amountInputType: AmountInputType,
    amount: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(size = SPACER_PADDING_8))
                .animateContentSize()
                .height(54.dp),
            value = if (amount.isNotEmpty()) DecimalFormat().format(amount.toDouble()).formatDecimalSeparator() else "0.0",
            readOnly = true,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                errorContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor =
                if (amountInputType == AmountInputType.SOURCE) {
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                focusedTextColor = if (amountInputType == AmountInputType.SOURCE) {
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )

        )
    }
}