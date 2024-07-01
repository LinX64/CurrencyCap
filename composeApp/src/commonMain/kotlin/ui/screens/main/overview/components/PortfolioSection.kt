package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import ui.components.main.VerticalBarCard
import ui.screens.main.overview.OverviewState
import ui.theme.colors.CurrencyColors

@Composable
internal fun PortfolioSection(
    state: OverviewState,
    usd: String = "USD",
    hazeState: HazeState
) {
    val isLoading = state is OverviewState.Loading

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 8.dp)
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

        VerticalBarCard(
            hazeState = hazeState,
            onTabSelected = { /* TODO */ },
        )
    }
}

@Composable
private fun InnerChartRow(
    isLoading: Boolean
) {
    Row(
        modifier = Modifier.padding(top = 8.dp),
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

//                ChangeIcon(
//                    modifier = if (isLoading) getPlaceHolder(Modifier.size(16.dp)) else Modifier.size(16.dp),
//                    isPositive = true,
//                    isLoading = isLoading,
//                    valueChange = 10.1
//                )
    }
}

@Composable
private fun InnerDropDown(
    isLoading: Boolean,
    usd: String
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(
            onClick = { expanded = !expanded },
            modifier = if (isLoading) getPlaceHolder(
                Modifier
                    .height(30.dp)
                    .clip(RoundedCornerShape(35))
            ) else Modifier
                .height(30.dp)
                .clip(RoundedCornerShape(35)),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(8.dp)
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier.padding(end = 10.dp)) else Modifier.padding(end = 10.dp),
                text = usd,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )

            Icon(
                modifier = if (isLoading) getPlaceHolder(Modifier.size(16.dp)) else Modifier.size(16.dp),
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
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

