package ui.screens.main.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import util.DateUtils.convertMillisToDate

@Composable
internal fun NewsFilterSection(
    modifier: Modifier = Modifier,
    sources: List<String>,
    onCloseClick: () -> Unit,
    onDoneClick: (startDate: String, endDate: String, sources: Set<String>) -> Unit
) {
    val defaultDate = convertMillisToDate(Clock.System.now().toEpochMilliseconds())
    var selectedStartDate by rememberSaveable { mutableStateOf(defaultDate) }
    var selectedEndDate by rememberSaveable { mutableStateOf(defaultDate) }
    var selectedSources by rememberSaveable { mutableStateOf(emptySet<String>()) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Filter by",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        ExpandableSourceButtonRow(
            sources = sources,
            selectedSourcesList = { selectedSources = it }
        )

        DateOfPublicationButton(
            onStartDateSelected = { selectedStartDate = it },
            onEndDateSelected = { selectedEndDate = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        FooterHorizontalButtons(
            onCloseClick = onCloseClick,
            onSetClick = { onDoneClick(selectedStartDate, selectedEndDate, selectedSources) }
        )
    }
}



