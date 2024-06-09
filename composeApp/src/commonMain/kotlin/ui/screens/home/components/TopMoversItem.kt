package ui.screens.home.components

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent

@Composable
internal fun TopMoversItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
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

            SubcomposeAsyncImage(
                modifier = Modifier.size(48.dp),
                model = icon,
                contentDescription = null
            ) {
                if (isLoading) {
                    ItemPlaceHolder(modifier = Modifier.size(48.dp))
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            ChangeIcon(data.valueChange)

            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "-42.64%",
                color = if (isColorRed) Color.Red else Color.Green,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

