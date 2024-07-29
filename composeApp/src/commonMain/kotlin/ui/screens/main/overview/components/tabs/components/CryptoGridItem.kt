package ui.screens.main.overview.components.tabs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.crypto_image
import domain.model.main.Crypto
import org.jetbrains.compose.resources.stringResource
import ui.components.base.GlassCard
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.formatNumber

@Composable
internal fun CryptoGridItem(
    cryptoItem: Crypto,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    GlassCard(
        modifier = Modifier.padding(end = SPACER_PADDING_8),
        isClickable = true,
        onCardClick = { onCryptoItemClick(cryptoItem.id, cryptoItem.symbol) }
    ) {
        Column(
            modifier = Modifier.size(width = 190.dp, height = 75.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(SPACER_PADDING_32).clip(CircleShape),
                    model = cryptoItem.image,
                    contentDescription = stringResource(Res.string.crypto_image)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = cryptoItem.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = cryptoItem.symbol.uppercase(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "$${formatNumber(cryptoItem.currentPrice)}",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "MC: $${formatNumber(cryptoItem.marketCap)}",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
