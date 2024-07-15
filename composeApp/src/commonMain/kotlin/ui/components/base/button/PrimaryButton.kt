package ui.components.base.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.colors.CurrencyColors

@Composable
internal fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    isEnabled: Boolean = true,
    onButtonClick: () -> Unit,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            CurrencyColors.Muted_Teal.primary,
            CurrencyColors.Green.primary
        )
    )

    Button(
        onClick = onButtonClick,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = gradient,
                shape = RoundedCornerShape(CARD_CORNER_RADIUS)
            )
            .clip(RoundedCornerShape(CARD_CORNER_RADIUS))
            .then(modifier),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}