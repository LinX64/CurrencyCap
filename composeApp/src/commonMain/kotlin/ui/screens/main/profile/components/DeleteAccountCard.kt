package ui.screens.main.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.chevron_right
import currencycap.composeapp.generated.resources.delete_account
import currencycap.composeapp.generated.resources.delete_your_account
import currencycap.composeapp.generated.resources.delete_your_account_warning
import org.jetbrains.compose.resources.stringResource
import ui.components.DottedShape
import ui.components.base.GlassCard
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors

@Composable
internal fun DeleteAccountCard(
    onDeleteAccountClicked: () -> Unit
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            CurrencyColors.Red.primary,
            CurrencyColors.Red.light
        )
    )

    GlassCard {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(20.dp)
                        .background(Color(0xFFE91E63))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(Res.string.delete_account),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(SPACER_PADDING_16))

            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = SPACER_PADDING_16)
                    .background(
                        brush = gradient,
                        shape = RoundedCornerShape(SPACER_PADDING_16)
                    ),
                onClick = { onDeleteAccountClicked() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(SPACER_PADDING_16)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(Res.string.delete_your_account),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Icon(
                        modifier = Modifier.padding(start = SPACER_PADDING_8),
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = stringResource(Res.string.chevron_right),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray, shape = DottedShape(step = 10.dp))
            )

            Spacer(modifier = Modifier.height(SPACER_PADDING_8))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.delete_your_account_warning),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }
    }
}