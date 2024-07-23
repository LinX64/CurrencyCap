package ui.screens.main.news.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.date_of_publication
import currencycap.composeapp.generated.resources.end_date
import currencycap.composeapp.generated.resources.start_date
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import ui.components.base.GlassCard
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.DateUtils.convertMillisToDate

@Composable
internal fun DateOfPublicationButton(
    onStartDateSelected: (String) -> Unit,
    onEndDateSelected: (String) -> Unit
) {
    GlassCard(
        isClickable = false
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACER_PADDING_8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(SPACER_PADDING_8))

                Text(
                    text = stringResource(Res.string.date_of_publication),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(SPACER_PADDING_8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StartDatePickerSection(
                    onDateSelected = onStartDateSelected
                )

                EndDatePickerSection(
                    onDateSelected = onEndDateSelected
                )
            }
        }
    }
}

@Composable
private fun StartDatePickerSection(
    onDateSelected: (String) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val defaultDate = convertMillisToDate(Clock.System.now().toEpochMilliseconds())
    val selectedDate = rememberSaveable { mutableStateOf(defaultDate) }

    Column {
        Text(
            modifier = Modifier.padding(SPACER_PADDING_8),
            text = stringResource(Res.string.start_date),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .padding(SPACER_PADDING_8),
            onClick = { showDialog.value = true },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            ),
            shape = RoundedCornerShape(size = SPACER_PADDING_16)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                text = selectedDate.value,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    if (showDialog.value) {
        BaseDatePickerDialog(
            onDismiss = { showDialog.value = false },
            onDateSelected = {
                showDialog.value = false
                selectedDate.value = it
                onDateSelected(selectedDate.value)
            }
        )
    }
}

@Composable
private fun EndDatePickerSection(
    onDateSelected: (String) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val defaultDate = convertMillisToDate(Clock.System.now().toEpochMilliseconds())
    val selectedDate = rememberSaveable { mutableStateOf(defaultDate) }

    Column {
        Text(
            modifier = Modifier.padding(SPACER_PADDING_8),
            text = stringResource(Res.string.end_date),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier.padding(SPACER_PADDING_8),
            onClick = { showDialog.value = true },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            ),
            shape = RoundedCornerShape(size = SPACER_PADDING_16)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                text = selectedDate.value,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    if (showDialog.value) {
        BaseDatePickerDialog(
            onDismiss = { showDialog.value = false },
            onDateSelected = {
                showDialog.value = false
                selectedDate.value = it
                onDateSelected(selectedDate.value)
            }
        )
    }
}

