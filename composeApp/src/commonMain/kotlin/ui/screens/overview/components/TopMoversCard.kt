package ui.screens.overview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
internal fun TopMoversCard(
    isLoading: Boolean,
    name: String,
    fullName: String
) {
    val hazeState = remember { HazeState() }
    MaterialsCard(
        modifier = Modifier
            .width(165.dp)
            .wrapContentHeight()
            .hazeChild(
                state = hazeState,
                shape = MaterialTheme.shapes.large,
                style = HazeMaterials.regular(),
            )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = name,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = fullName,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "$2,199.24",
                color = Color.Gray,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            TopMoversChart(
                modifier = if (isLoading) getPlaceHolder(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .width(40.dp),
                ) else Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .width(40.dp),
                list = mockAssetInfo.lastDayChange
            )

            Spacer(modifier = Modifier.height(32.dp))

            BottomRow(isLoading = isLoading)
        }
    }
}

@Composable
private fun BottomRow(
    isLoading: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = "+26.46 %",
            color = Color.Green,
            fontSize = 14.sp
        )

//        ChangeIcon(
//            valueChange = 26,
//            isLoading = isLoading
//        )
    }
}

@Composable
private fun MaterialsCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.then(modifier),
        shape = RoundedCornerShape(35.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Box(Modifier.padding(16.dp)) {
            content()
        }
    }
}