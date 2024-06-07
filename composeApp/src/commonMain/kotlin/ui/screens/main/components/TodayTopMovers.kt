package ui.screens.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import ui.common.getPlaceHolderDrawable

@Composable
internal fun TodayTopMovers() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Today's Top Movers",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow {
            items(4) {
                TopMovers(icon = "")
            }
        }
    }
}

@Composable
private fun TopMovers(
    modifier: Modifier = Modifier,
    icon: String,
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
                placeholder = getPlaceHolderDrawable(),
                model = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1200px-Bitcoin.svg.png",
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "BTC",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
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

