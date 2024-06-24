package com.client.currencycap.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import org.koin.core.context.stopKoin
import ui.App
import ui.theme.AppM3Theme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            // TODO: Add dark/light support
            AppM3Theme(dark = true) {
                App()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        stopKoin()
    }
}