package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.components.CenteredColumn
import ui.screens.initial.get_verified.components.CodeTextField

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    KoinPreview {
        CenteredColumn {
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
                CodeTextField(
                    value = "filll",
                    length = 6,
                    onValueChange = { },
                )
            }
        }
    }
}