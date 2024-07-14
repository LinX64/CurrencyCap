package ui.screens.main.news.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseDatePickerDialog(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onOkClick: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1689359122000)

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onCancelClick,
        confirmButton = {
            TextButton(
                onClick = onOkClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Ok",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContentColor = MaterialTheme.colorScheme.surface,
            )
        )
    }
}
