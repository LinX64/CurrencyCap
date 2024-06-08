package ui.screens.exchange.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.screens.exchange.ExchangeState

@Composable
internal fun ToDropDown(
    onToChange: (String) -> Unit,
    exchangeState: ExchangeState
) = when (exchangeState) {
    is ExchangeState.Success -> handleSuccess(
        onToChange = onToChange,
        exchangeState = exchangeState
    )

    is ExchangeState.Error -> {
        // todo: handle error
    }

    else -> Unit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun handleSuccess(
    modifier: Modifier = Modifier,
    onToChange: (String) -> Unit,
    exchangeState: ExchangeState.Success
) {
    val options = exchangeState.rates.map { it.symbol }.sortedBy { it.take(2) }
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
            value = selectedOptionText.toString(),
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onToChange(selectionOption.toString())
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
