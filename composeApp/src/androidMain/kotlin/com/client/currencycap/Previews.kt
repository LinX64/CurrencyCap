package com.client.currencycap

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.components.CenteredColumn
import ui.screens.initial.reset_password.ResetPasswordScreen

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {
        CenteredColumn {
            ResetPasswordScreen(
                padding = PaddingValues(16.dp),
                onNavigateToLogin = {},
                onMessage = {}
            )
        }
    }
}

