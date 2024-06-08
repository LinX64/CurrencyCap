package ui.screens.exchange.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import domain.model.DataDao
import ui.screens.exchange.ExchangeState

@Composable
internal fun FromDropDown(
    onFromChange: (String) -> Unit,
    exchangeState: ExchangeState
) = when (exchangeState) {
    is ExchangeState.Success -> handleFromSuccess(
        rates = exchangeState.rates,
        onFromChange = onFromChange,
    )

    is ExchangeState.Error -> {
        // todo: handle error
    }

    else -> Unit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun handleFromSuccess(
    modifier: Modifier = Modifier,
    rates: List<DataDao>,
    onFromChange: (String) -> Unit
) {
    val options = rates.map { it.symbol }.sortedBy { it.take(2) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    val containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
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
                        onFromChange(selectionOption)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}
