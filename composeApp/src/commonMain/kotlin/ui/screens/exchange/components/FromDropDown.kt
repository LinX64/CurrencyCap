package ui.screens.exchange.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.RateDto
import ui.components.getDummyRates
import ui.screens.exchange.ExchangeState
import util.getTextFieldOptions

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
        onFromChange = onFromChange,
    )

    else -> HandleFromDropDown(
        rates = getDummyRates(),
        isLoading = true,
        onFromChange = onFromChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HandleFromDropDown(
    modifier: Modifier = Modifier,
    rates: List<RateDto>,
    onError: String = "",
    isLoading: Boolean = false,
    onFromChange: (String) -> Unit
) {
    val options = rates.getTextFieldOptions()
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[1]) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredOptions = options.filter { it.contains(searchQuery, ignoreCase = true) }
    val containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    LaunchedEffect(Unit) {
        if (options.isNotEmpty()) {
            val initialSelectedCountryCode = options[1].split(" ")[2].take(2)
            onFromChange(initialSelectedCountryCode)
        }
    }

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
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            ExposedDropdownMenu(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                Column {
                    if (expanded) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }

                    filteredOptions.forEach { selectionOption ->
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

                                val selectedCountryCode =
                                    if (selectionOption.isNotEmpty()) selectionOption.split(" ")[2].take(2) else "AF"
                                onFromChange(selectedCountryCode)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        }
    }
}