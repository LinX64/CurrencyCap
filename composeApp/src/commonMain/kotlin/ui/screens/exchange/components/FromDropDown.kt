package ui.screens.exchange.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.RateDto
import ui.common.getCountryFlag
import ui.common.getCountryName
import ui.components.getDummyRates
import ui.screens.exchange.ExchangeState

@Composable
internal fun FromDropDown(
    onFromChange: (String) -> Unit,
    exchangeState: ExchangeState
) = when (exchangeState) {
    is ExchangeState.Success -> HandleFromDropDown(
        rates = exchangeState.rates,
        onFromChange = onFromChange
    )

    is ExchangeState.Error -> HandleFromDropDown(
        rates = getDummyRates(),
        onError = exchangeState.message.ifEmpty { "Error while fetching rates" },
        onFromChange = onFromChange,
    )

    else -> HandleFromDropDown(
        rates = getDummyRates(),
        onFromChange = onFromChange,
        isLoading = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HandleFromDropDown(
    modifier: Modifier = Modifier,
    rates: List<RateDto>,
    onError: String = "",
    isLoading: Boolean = false,
    onFromChange: (String) -> Unit,
) {
    val options = rates.map { it.symbol }.sortedBy { it }.map { symbol ->
        val getSymbol = symbol.take(2)
        with(getSymbol) {
            val countryName = getCountryName()
            val flag = getCountryFlag()
            "$flag  $symbol - $countryName"
        }
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    Box {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                value = onError.ifEmpty { selectedOptionText },
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)
            )
            ExposedDropdownMenu(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = selectionOption,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false

                            val selectedCountryCode = if (selectionOption.isNotEmpty()) selectionOption.split(" ")[2] else "AED"
                            onFromChange(selectedCountryCode)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).size(24.dp))
        }
    }
}
