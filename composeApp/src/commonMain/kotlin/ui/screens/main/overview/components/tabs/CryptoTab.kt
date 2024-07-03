package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screens.main.overview.components.ChangeIcon
import ui.screens.main.overview.components.TopMoversChart
import ui.screens.main.overview.components.getPlaceHolder
import ui.screens.main.overview.components.mockAssetInfo
import ui.theme.colors.CurrencyColors

@Composable
internal fun CryptoContent(
    isLoading: Boolean = false,
    usd: String = "USD"
) {
    Column(
        modifier = Modifier.wrapContentWidth(),
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = "Portfolio Balance",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "$4,273.94",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            InnerDropDown(isLoading, usd)
        }

        Spacer(modifier = Modifier.height(8.dp))

        InnerChartRow(isLoading)
    }
}

@Composable
internal fun InnerChartRow(
    isLoading: Boolean
) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopMoversChart(
            modifier = if (isLoading) getPlaceHolder(
                Modifier
                    .width(120.dp)
                    .height(60.dp)
            ) else Modifier
                .width(120.dp)
                .height(60.dp),
            lighterColor = CurrencyColors.Green.light,
            lightLineColor = CurrencyColors.Green.primary,
            list = mockAssetInfo.lastDayChange
        )

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = "BTC: 0.2398467",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )

        ChangeIcon(
            isPositive = true,
            isLoading = isLoading,
            valueChange = 10.1
        )
    }
}

@Composable
private fun InnerDropDown(
    isLoading: Boolean,
    usd: String
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = if (isLoading) Modifier.wrapContentSize() else Modifier.wrapContentSize(),
            onClick = { expanded = !expanded },
            shape = RoundedCornerShape(35)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = usd,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = if (isLoading) getPlaceHolder(Modifier.size(16.dp)) else Modifier.size(16.dp),
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text("USD") },
                onClick = {
                    // TODO: Handle USD selection
                    expanded = false
                }
            )
            // TODO: Add more currency options here
        }
    }
}

