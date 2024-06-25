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
import ui.components.getDummyRates
import ui.screens.exchange.ExchangeState
import ui.screens.exchange.ExchangeState.Error
import ui.screens.exchange.ExchangeState.Success
import util.getTextFieldOptions

@Composable
internal fun ToDropDown(
    onToChange: (String) -> Unit,
    exchangeState: ExchangeState
) = when (exchangeState) {
    is Success -> HandleToDropDown(
        onToChange = onToChange,
        rates = exchangeState.rates
    )

    is Error -> HandleToDropDown(
        onToChange = onToChange,
        rates = getDummyRates(),
        onError = exchangeState.message.ifEmpty { "Error while fetching rates" }
    )

    else -> HandleToDropDown(
        rates = getDummyRates(),
        onToChange = onToChange,
        isLoading = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HandleToDropDown(
    modifier: Modifier = Modifier,
    rates: List<RateDto>,
    onError: String = "",
    isLoading: Boolean = false,
    onToChange: (String) -> Unit
) {
    val options = rates.getTextFieldOptions()
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[1]) }
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
                singleLine = true,
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

                            val selectedCountryCode =
                                if (selectionOption.isNotEmpty()) selectionOption.split(" ")[2].take(2) else "AF"
                            onToChange(selectedCountryCode)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).size(24.dp))
        }
    }
}