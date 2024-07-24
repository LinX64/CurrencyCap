package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.today_top_movers
import org.jetbrains.compose.resources.stringResource
import ui.common.formatCurrentTotal
import ui.theme.AppDimensions.ICON_SIZE_48
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun TopMoversItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    icon: String,
    name: String,
    symbol: String,
    maxSupply: Double
) {
    val isColorRed = remember { mutableStateOf(maxSupply < 0) }

    Column(
        modifier = modifier.padding(SPACER_PADDING_8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp,
            ),
            shape = RoundedCornerShape(55.dp)
        ) {

            SubcomposeAsyncImage(
                modifier = Modifier.size(ICON_SIZE_48),
                model = icon,
                contentDescription = stringResource(Res.string.today_top_movers),
                contentScale = ContentScale.Inside
            ) {
                if (isLoading) {
                    ItemPlaceHolder(modifier = Modifier.size(ICON_SIZE_48))
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = symbol,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //ChangeIcon(maxSupply.toInt())

            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = formatCurrentTotal(maxSupply.toLong()),
                color = if (isColorRed.value) Color.Red else Color.Green,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

