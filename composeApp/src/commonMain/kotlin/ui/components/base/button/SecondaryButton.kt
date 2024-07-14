package ui.components.base.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.theme.colors.CurrencyColors

@Composable
internal fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: Dp = 4.dp,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onButtonClick: () -> Unit,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            CurrencyColors.Purple.primary,
            CurrencyColors.Purple.light
        )
    )

    Button(
        modifier = modifier
            .background(
                brush = gradient,
                shape = RoundedCornerShape(35.dp)
            )
            .clip(RoundedCornerShape(35.dp))
            .then(modifier),
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Text(
            modifier = Modifier.padding(textPadding),
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}