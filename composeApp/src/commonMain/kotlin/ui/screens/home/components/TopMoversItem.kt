package ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_48
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun TopMoversItem(
    modifier: Modifier = Modifier,
    icon: String,
    name: String,
) {
    val isColorRed = remember { data.valueChange < 0 }
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp,
            ),
            shape = RoundedCornerShape(55.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp),
                placeholder = painterResource(Res.drawable.baseline_monetization_on_48),
                model = icon,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        BottomIconText(isColorRed)
    }
}

@Composable
private fun BottomIconText(isColorRed: Boolean) {
    Row(
        modifier = Modifier.padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChangeIcon(data.valueChange)

        Text(
            text = "-42.64%",
            color = if (isColorRed) Color.Red else Color.Green,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun TopMoversItemLoading(
    modifier: Modifier = Modifier,
) {
    val isColorRed = remember { data.valueChange < 0 }
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp,
            ),
            shape = RoundedCornerShape(55.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "BTC",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        BottomIconText(isColorRed)
    }
}

