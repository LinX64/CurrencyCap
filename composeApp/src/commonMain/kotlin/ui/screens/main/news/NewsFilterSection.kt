package ui.screens.main.news

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
import di.koinViewModel
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import ui.screens.main.news.NewsViewEvent.OnSetClick
import ui.screens.main.news.components.DateOfPublicationButton
import ui.screens.main.news.components.ExpandableSourceButtonRow
import ui.screens.main.news.components.FooterHorizontalButtons
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.DateUtils.convertMillisToDate

@Composable
internal fun NewsFilterSection(
    newsViewModel: NewsViewModel = koinViewModel(),
    onCloseClick: () -> Unit
) {
    val defaultDate = convertMillisToDate(Clock.System.now().toEpochMilliseconds())
    var selectedStartDate by rememberSaveable { mutableStateOf(defaultDate) }
    var selectedEndDate by rememberSaveable { mutableStateOf(defaultDate) }
    var selectedSources by rememberSaveable { mutableStateOf(emptySet<String>()) }

    Column(
        modifier = Modifier
            .padding(horizontal = SPACER_PADDING_16, vertical = SPACER_PADDING_16),
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_16)
    ) {
        Text(
            modifier = Modifier.padding(top = SPACER_PADDING_16),
            text = stringResource(Res.string.filter_by),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        ExpandableSourceButtonRow(
            sources = newsViewModel.sources.value,
            selectedSourcesList = { selectedSources = it.toSet() }
        )

        DateOfPublicationButton(
            onStartDateSelected = { selectedStartDate = it },
            onEndDateSelected = { selectedEndDate = it }
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        FooterHorizontalButtons(
            onCloseClick = onCloseClick,
            onSetClick = {
                newsViewModel.handleEvent(
                    OnSetClick(
                        startDate = selectedStartDate,
                        endDate = selectedEndDate,
                        selectedSources = selectedSources
                    )
                )

                onCloseClick()
            }
        )
    }
}



