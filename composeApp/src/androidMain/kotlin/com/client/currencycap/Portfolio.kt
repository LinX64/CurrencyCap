package com.client.currencycap

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowOutward
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screens.overview.components.TopMoversChart
import ui.screens.overview.components.mockAssetInfo

@Composable
fun PortfolioSection() {
    var expanded by remember { mutableStateOf(false) }
    val usd = "USD"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
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
                text = "$4,273.94",
                color = Color(0xFF00DA74),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box {
                Button(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .height(30.dp)
                        .clip(RoundedCornerShape(50)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF202020)),
                    contentPadding = PaddingValues(8.dp),

                    ) {
                    Text(text = usd, color = Color.White, fontSize = 14.sp)
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(Color(0xFF202020))
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    DropdownMenuItem(
                        text = { Text("USD") },
                        onClick = {
                            // Handle USD selection
                            expanded = false
                        }
                    )
                    // Add more currency options here
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopMoversChart(
                modifier = Modifier
                    .width(120.dp)
                    .height(60.dp),
                lighterColor = Color(0xFF00DA74),
                lightLineColor = Color(0xFF00DA74),
                list = mockAssetInfo.lastDayChange
            )

            Text(
                text = "BTC: 0.2398467",
                color = Color(0xFFA3A3A8),
                fontSize = 14.sp,
            )

            Icon(
                // Replace with an up arrow icon
                imageVector = Icons.Filled.ArrowOutward, // Example icon
                contentDescription = null,
                tint = Color(0xFF00DA74),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun PortfolioPreview() {
    KoinPreview {
        DarkBackground {
            PortfolioSection()
        }
    }
}