package ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.client.currencycap.ui.common.getPlaceHolderDrawable
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import domain.model.RateDao
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.BulbBackground
import ui.theme.AppM3Theme
import ui.theme.colors.CurrencyColors

@Composable
fun RateHorizontalItem(
    modifier: Modifier = Modifier,
    icon: String = "https://example.com/image.jpg",
    rate: RateDao
) {
    val hazeState = remember { HazeState() }

    Box(
        modifier.haze(
            state = hazeState,
            style = HazeDefaults.style(
                tint = Color.White.copy(alpha = 0.1f),
                blurRadius = 1.dp
            ),
        )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .hazeChild(
                    state = hazeState,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    modifier = Modifier.size(48.dp),
                    placeholder = getPlaceHolderDrawable(),
                    model = "https://icons.veryicon.com/png/o/business/business-finance/coin-11.png",
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = rate.code,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = rate.sell.toString(),
                        color = Color.LightGray.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.padding(start = 100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "$ 1,000",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {

                            // TODO: add upward arrow icon and downward arrow icon
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = null,
                                tint = CurrencyColors.Text_Green
                            )

                            Text(
                                text = "Buy",
                                color = CurrencyColors.Text_Green,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreviewLight() {
    AppM3Theme(dark = false) {
        BulbBackground {
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 350.dp),
                rows = GridCells.Fixed(3),
                contentPadding = PaddingValues(15.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) { index ->
                    RateHorizontalItem(
                        rate = RateDao(
                            code = "USD",
                            sell = 1,
                            buy = 1
                        )
                    )
                }
            }
        }
    }
}
