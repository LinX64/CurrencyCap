package ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import currencycap.composeapp.generated.resources.baseline_monetization_on_24
import domain.model.RateDao
import org.jetbrains.compose.resources.painterResource
import util.formatToPrice

@Composable
internal fun RateItem(
    modifier: Modifier = Modifier,
    icon: String,
    rate: RateDao
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(
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
                placeholder = painterResource(Res.drawable.baseline_monetization_on_24),
                error = painterResource(Res.drawable.baseline_monetization_on_24),
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