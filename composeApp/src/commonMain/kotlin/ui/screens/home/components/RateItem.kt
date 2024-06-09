package ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_48
import domain.model.RateDao
import org.jetbrains.compose.resources.painterResource
import ui.components.BlurColumn
import util.formatToPrice

@Composable
internal fun RateItem(
    modifier: Modifier = Modifier,
    icon: String,
    rate: RateDao
) {
    BlurColumn {
        Column(
            modifier = modifier.padding(
                start = 32.dp,
                end = 32.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp),
                placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                error = painterResource(Res.drawable.baseline_monetization_on_48),
                model = icon,
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = rate.code,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )

            val formattedPrice = rate.sell.formatToPrice()
            Text(
                text = "$formattedPrice t",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
internal fun RateItemLoading(
    modifier: Modifier = Modifier
) {
    BlurColumn {
        Column(
            modifier = modifier.padding(
                start = 32.dp,
                end = 32.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}