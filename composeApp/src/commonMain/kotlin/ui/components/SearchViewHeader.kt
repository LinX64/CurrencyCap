package ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_letters_ai
import currencycap.composeapp.generated.resources.ic_search_normal
import currencycap.composeapp.generated.resources.info
import currencycap.composeapp.generated.resources.search
import currencycap.composeapp.generated.resources.type_to_search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun SearchViewHeader(
    onSearchCardClicked: () -> Unit,
    onCircleButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(CARD_CORNER_RADIUS),
            onClick = onSearchCardClicked,
            border = BorderStroke(
                width = 1.dp,
                color = Color.Gray.copy(alpha = .1f),
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Icon(
                    modifier = Modifier.padding(SPACER_PADDING_16),
                    painter = painterResource(Res.drawable.ic_search_normal),
                    contentDescription = stringResource(Res.string.search),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Text(
                    modifier = Modifier.padding(SPACER_PADDING_16),
                    text = stringResource(Res.string.type_to_search),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.width(SPACER_PADDING_8))

        CircleButton(onCircleButtonClicked = onCircleButtonClicked)
    }
}

@Composable
private fun CircleButton(
    onCircleButtonClicked: () -> Unit
) {
    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Gray.copy(alpha = .1f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        IconButton(
            modifier = Modifier.size(64.dp),
            onClick = onCircleButtonClicked
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_letters_ai),
                contentDescription = stringResource(Res.string.info),
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}