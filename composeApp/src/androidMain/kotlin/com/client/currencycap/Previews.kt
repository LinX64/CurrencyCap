package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.screens.main.profile.components.ProfileCard

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            ProfileCard(
                profileImage = "invenire",
                fullName = "Terry Keller",
                email = "donnell.gutierrez@example.com",
                phone = "(467) 557-0264"
            )
        }
    }
}

