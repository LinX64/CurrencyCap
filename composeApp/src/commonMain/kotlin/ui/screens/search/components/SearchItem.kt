package ui.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import domain.model.DataDao
import ui.common.formatCurrentTotal
import ui.components.BlurColumn
import ui.screens.home.components.ChangeIcon
import ui.screens.home.components.ItemPlaceHolder
import ui.screens.home.components.data
import ui.screens.home.components.getPlaceHolder
import util.getIconBy

@Composable
internal fun SearchItem(
    modifier: Modifier = Modifier,
    cardBackground: Color = Color.Transparent,
    cardSize: Dp = 150.dp,
    dataDao: DataDao,
    isLoading: Boolean = false
) {
    BlurColumn {
        Card(
            modifier = modifier
                .size(cardSize)
                .clip(RoundedCornerShape(15.dp)),
            colors = CardDefaults.cardColors(containerColor = cardBackground)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.size(150.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(
                            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                            text = formatCurrentTotal(data.currentTotal),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelMedium
                        )

                        ChangeIcon(valueChange = data.valueChange, isLoading = isLoading)
                    }

                    SubcomposeAsyncImage(
                        modifier = Modifier.size(24.dp).clip(CircleShape),
                        model = getIconBy(dataDao.symbol),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    ) {
                        if (isLoading) {
                            ItemPlaceHolder(modifier = Modifier.size(24.dp))
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                        text = dataDao.symbol,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                        text = "${dataDao.currencySymbol}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                        text = formatCurrentTotal(data.currentTotal),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
