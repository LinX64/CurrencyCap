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
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.filter_by
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_8
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
            .padding(horizontal = CARD_CORNER_RADIUS, vertical = CARD_CORNER_RADIUS),
        verticalArrangement = Arrangement.spacedBy(CARD_CORNER_RADIUS)
    ) {
        Text(
            modifier = Modifier.padding(top = CARD_CORNER_RADIUS),
            text = stringResource(Res.string.filter_by),
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

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        FooterHorizontalButtons(
            onCloseClick = onCloseClick,
            onSetClick = { onDoneClick(selectedStartDate, selectedEndDate, selectedSources) }
        )
    }
}



