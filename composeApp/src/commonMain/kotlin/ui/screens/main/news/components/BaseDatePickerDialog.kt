package ui.screens.main.news.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.cancel
import currencycap.composeapp.generated.resources.ok
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import util.DateUtils.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseDatePickerDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit,
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= Clock.System.now().toEpochMilliseconds()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = { ConfirmButton(onDateSelected, selectedDate, onDismiss) },
        dismissButton = { DismissButton(onDismiss) }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContentColor = MaterialTheme.colorScheme.surface,
                selectedYearContentColor = MaterialTheme.colorScheme.surface,
            )
        )
    }
}

@Composable
private fun ConfirmButton(
    onDateSelected: (String) -> Unit,
    selectedDate: String,
    onDismiss: () -> Unit
) {
    TextButton(
        onClick = {
            onDateSelected(selectedDate)
            onDismiss()
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.ok),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DismissButton(onCancelClick: () -> Unit) {
    TextButton(
        onClick = onCancelClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.cancel),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

