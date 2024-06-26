package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.components.main.BottomNavigationBar
import ui.theme.AppM3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, device = "id:pixel_3a")
private fun CardPreview() {
    val hazeState = remember { HazeState() }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    AppM3Theme(dark = true) {
        DarkBackground {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                BottomNavigationBar(
                    onTabSelected = { tab -> },
                    scrollBehavior = scrollBehavior
                )
            }
        }
    }
}
