package ui.screens.main.news.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.components.base.GlassCard

@Composable
internal fun DateOfPublicationButton() {
    GlassCard(
        isClickable = false
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Date of publication",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StartDatePickerSection()

            EndDatePickerSection()
        }
    }
}

@Composable
private fun StartDatePickerSection() {
    Column {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Start Date",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(size = 35.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.09f),
                    shape = RoundedCornerShape(size = 35.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                text = "2021-01-01",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EndDatePickerSection() {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000)
    val showDialog = remember { mutableStateOf(false) }

    Column {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "End Date",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(size = 35.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.09f),
                    shape = RoundedCornerShape(size = 35.dp)
                )
                .clickable { showDialog.value = true } // Open date picker on click
        ) {
//            Text(
//                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
//                text = "Selected date: ${
//                    datePickerState.selectedDateMillis?.let {
//                        SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(it))
//                    } ?: "no input"
//                }",
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.onSurface
//            )
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                text = "2021-01-01",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            println("date: ${datePickerState.selectedDateMillis}")
        }

        if (showDialog.value) {
            DatePickerDialog(showDialog, datePickerState)
        }
    }
}

